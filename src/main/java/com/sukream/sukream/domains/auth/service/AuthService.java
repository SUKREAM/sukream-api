package com.sukream.sukream.domains.auth.service;

import com.sukream.sukream.commons.domain.response.Response;
import com.sukream.sukream.domains.auth.domain.request.LoginRequest;
import com.sukream.sukream.domains.auth.domain.request.SignInRequest;
import com.sukream.sukream.domains.auth.domain.response.SocialOAuthResponse;
import com.sukream.sukream.domains.auth.domain.response.TokenResponse;
import com.sukream.sukream.domains.auth.mapper.SignInMapper;
import com.sukream.sukream.domains.auth.repository.UserInfoRepository;
import com.sukream.sukream.domains.auth.security.JwtTokenProvider;
import com.sukream.sukream.domains.user.domain.entity.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import static com.sukream.sukream.commons.constants.AuthConstants.*;
import static com.sukream.sukream.commons.constants.CommonConstants.NAME;
import static com.sukream.sukream.commons.constants.ErrorCode.ERR_UNKNOWN;
import static com.sukream.sukream.commons.constants.ErrorCode.RESOURCE_NOT_FOUND;
import static com.sukream.sukream.commons.utils.DateUtil.ONE_MONTH_IN_MILLISECONDS;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserInfoRepository userInfoRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final MessageDelegate messageDelegate;
    private final EmailClient emailClient;
    private final SignInMapper signInMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthDelegate authDelegate;


    public HttpStatus findEmail(String phoneNumber) {
        return messageDelegate.sendSMS(phoneNumber);
    }

    public HttpStatus findPassword(String email){
        Users userInfo = userInfoRepository.findUsersByEmail(email).get();
        return emailClient.sendOneEmail(userInfo.getEmail(), userInfo.getPassword());
    }


    public TokenResponse login(HttpServletResponse response, LoginRequest request){
        Users userInfo = userInfoRepository.findUsersByEmail(request.getEmail())
                .filter(m -> passwordEncoder.matches(request.getPw(), m.getPassword()))
                .orElseThrow(IllegalArgumentException::new);

        return this.makeToken(response, userInfo);
    }

    public TokenResponse signIn(HttpServletResponse response, SignInRequest signInRequest){
        signInRequest.setPassword((authDelegate.passwordEncoding(signInRequest.getPassword())));

        Users users = signInMapper.toEntity(signInRequest);
        if(users != null) {
            userInfoRepository.save(users);
        }

        return this.makeToken(response, Objects.requireNonNull(users));
    }


    public Response doVerification(HttpServletResponse response, String provider, String accessToken){

        String baseUrl;
        switch (provider) {
            case GOOGLE -> baseUrl = GOOGLE_VALIDATE_URL;
            case NAVER -> baseUrl = NAVER_VALIDATE_URL;
            default -> throw new RuntimeException();
        }

        WebClient webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        AtomicReference<Response> errorResponse = new AtomicReference<>(new Response());
        SocialOAuthResponse oAuthResponse =
                webClient
                        .get()
                        .header(AUTHORIZATION, BEARER + accessToken)
                        .retrieve()
                        .bodyToMono(SocialOAuthResponse.class)
                        .onErrorResume(error -> {
                            if (error instanceof WebClientResponseException ex) {
                                errorResponse.set(Response.error(ex.getStatusCode().is4xxClientError() ? RESOURCE_NOT_FOUND : ERR_UNKNOWN));
                            }
                            return Mono.empty();
                        })
                        .block();

        if(!errorResponse.get().isSuccess())
            return errorResponse.get();

        Users usersInfo;
        assert oAuthResponse != null;
        switch (provider) {
            case GOOGLE -> usersInfo = userInfoRepository.findUsersByEmail(oAuthResponse.getEmail()).get();
            case NAVER -> {
                assert oAuthResponse.getResponse() != null;
                usersInfo = userInfoRepository.findUsersByEmail(oAuthResponse.getResponse().getEmail()).get();
            }
            default -> throw new RuntimeException();
        }

        if (usersInfo == null)
            return Response.error(RESOURCE_NOT_FOUND);

        return this.makeToken(response, usersInfo);
    }

    private TokenResponse makeToken(HttpServletResponse response, Users usersInfo){
        Claims claims = Jwts.claims().setSubject(usersInfo.getEmail());
        claims.put(NAME, usersInfo.getName());

        String accessToken = jwtTokenProvider.createAccessToken(claims);
        String refreshToken = jwtTokenProvider.createRefreshToken(claims);

        Cookie cookie = new Cookie(REFRESH_TOKEN_STR, refreshToken);
        cookie.setMaxAge((int) ONE_MONTH_IN_MILLISECONDS);
        response.addCookie(cookie);

        return TokenResponse.builder()
                .accessToken(accessToken)
                .build();
    }

}
