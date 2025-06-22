package com.sukream.sukream.domains.auth.service;

import com.sukream.sukream.commons.config.security.SocialOAuthProviderConfig;
import com.sukream.sukream.commons.constants.AuthConstants;
import com.sukream.sukream.commons.domain.response.Response;
import com.sukream.sukream.domains.auth.domain.request.LoginRequest;
import com.sukream.sukream.domains.auth.domain.request.SignInRequest;
import com.sukream.sukream.domains.auth.domain.response.SocialOAuthResponse;
import com.sukream.sukream.domains.auth.domain.response.SocialTokenResponse;
import com.sukream.sukream.domains.auth.domain.response.TokenResponse;
import com.sukream.sukream.domains.auth.mapper.SignInMapper;
import com.sukream.sukream.domains.auth.repository.UserInfoRepository;
import com.sukream.sukream.domains.auth.security.JwtTokenProvider;
import com.sukream.sukream.domains.user.domain.entity.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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

    @Value("${naver.client-id}")
    private String naverClientId;

    @Value("${naver.client-secret}")
    private String naverClientSecret;

    @Value("${google.client-id}")
    private String googleClientId;

    @Value("${google.client-secret}")
    private String googleClientSecret;

    @Value("${kakao.client-id}")
    private String kakaoClientId;

    @Value("${redirect-uri}")
    private String redirectUri;


    private SocialOAuthProviderConfig getConfig(String provider) {
        return switch (provider) {
            case GOOGLE -> new SocialOAuthProviderConfig(
                    googleClientId, googleClientSecret, redirectUri,
                    GOOGLE_TOKEN_URL, GOOGLE_VALIDATE_URL);
            case NAVER -> new SocialOAuthProviderConfig(
                    naverClientId, naverClientSecret, redirectUri,
                    NAVER_TOKEN_URL, NAVER_VALIDATE_URL);
            case KAKAO -> new SocialOAuthProviderConfig(
                    kakaoClientId, null, redirectUri,
                    KAKAO_TOKEN_URL, KAKAO_USER_INFO_URL);
            default -> throw new RuntimeException("Unsupported provider");
        };
    }

    private String createSecureRandomPassword() {
        final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final String lower = "abcdefghijklmnopqrstuvwxyz";
        final String digits = "0123456789";
        final String symbols = "!@#$%^&*()_+";
        final String allChars = upper + lower + digits + symbols;

        SecureRandom random = new SecureRandom();

        // 필수 조건 문자 1개씩
        List<Character> passwordChars = Stream.of(
                upper.charAt(random.nextInt(upper.length())),
                lower.charAt(random.nextInt(lower.length())),
                digits.charAt(random.nextInt(digits.length())),
                symbols.charAt(random.nextInt(symbols.length()))
        ).collect(Collectors.toCollection(ArrayList::new));

        // 나머지 랜덤 문자 추가
        int totalLength = random.nextInt(8) + 8;
        IntStream.range(0, totalLength - 4)
                .mapToObj(i -> allChars.charAt(random.nextInt(allChars.length())))
                .forEach(passwordChars::add);

        // 셔플 & 문자열 변환
        Collections.shuffle(passwordChars, random);

        return passwordChars.stream()
                .map(String::valueOf)
                .collect(Collectors.joining());
    }


    public HttpStatus findEmail(String phoneNumber) {
        return messageDelegate.sendSMS(phoneNumber);
    }

    @Transactional
    public HttpStatus findPassword(String email){
        Users userInfo = userInfoRepository.findUsersByEmail(email).get();
        String pw = createSecureRandomPassword();
        userInfo.updatePassword((authDelegate.passwordEncoding(createSecureRandomPassword())));
        return emailClient.sendOneEmail(userInfo.getEmail(), pw);
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


    public Response doVerification(HttpServletResponse response, String provider, String code) {
        SocialOAuthProviderConfig config = getConfig(provider);
        if (config == null) throw new RuntimeException("Unsupported provider");

        // 1. Access Token 요청
        WebClient tokenClient = WebClient.builder()
                .baseUrl(config.getTokenUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .build();

        String decode = URLDecoder.decode(code, StandardCharsets.UTF_8);


        SocialTokenResponse tokenResponse = tokenClient
                .post()
                .uri(uriBuilder -> uriBuilder
                        .queryParam(GRANT_TYPE, AUTHENTICATION_CODE)
                        .queryParam(CODE, decode)
                        .queryParam(CLIENT_ID, config.getClientId())
                        .queryParam(CLIENT_SECRET, config.getClientSecret())
                        .queryParam(REDIRECT_URI, config.getRedirectUri())
                        .build())
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().isError()) {
                        return clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> {
                                    log.error("OAuth token request failed with status: {}, body: {}",
                                            clientResponse.statusCode(), errorBody);
                                    return Mono.error(new RuntimeException("OAuth 토큰 요청 실패: " + errorBody));
                                });
                    } else {
                        return clientResponse.bodyToMono(SocialTokenResponse.class);
                    }
                })
                .block();

        if (tokenResponse == null || tokenResponse.getAccess_token() == null) {
            return Response.error(ERR_UNKNOWN);
        }

        String accessToken = tokenResponse.getAccess_token();

        // 2. 사용자 정보 요청
        WebClient userClient = WebClient.builder()
                .baseUrl(config.getUserInfoUrl())
                .defaultHeader(HttpHeaders.AUTHORIZATION, BEARER + accessToken)
                .build();

        SocialOAuthResponse oAuthResponse = userClient
                .get()
                .retrieve()
                .onStatus(status -> status.isError(), clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .map(errorBody -> {
                                log.error("OAuth user info error response: {}", errorBody);
                                return new RuntimeException("User info request failed: " + errorBody);
                            })
                            .flatMap(Mono::error);
                })
                .bodyToMono(SocialOAuthResponse.class)
                .block();


        if (oAuthResponse == null) {
            return Response.error(RESOURCE_NOT_FOUND);
        }

        // 3. 이메일 식별자 추출
        String email;
        String oauthId;
        String name;

        switch (provider) {
            case GOOGLE -> {
                email = oAuthResponse.getEmail();
                oauthId = oAuthResponse.getId();
                name = oAuthResponse.getName();
            }
            case NAVER -> {
                email = oAuthResponse.getResponse().getEmail();
                oauthId = oAuthResponse.getResponse().getId();
                name = oAuthResponse.getResponse().getName();
            }
            case KAKAO -> {
                email = oAuthResponse.getKakao_account().getEmail();
                oauthId = oAuthResponse.getId();
                name = oAuthResponse.getProperties().getNickname();
            }
            default -> throw new RuntimeException("Unsupported provider");
        }

        // 4. 사용자 조회 또는 등록
        Users user = userInfoRepository.findUsersByEmail(email)
                .orElseGet(() -> {
                    Users newUser = Users.builder()
                            .name(name)
                            .email(email)
                            .password(UUID.randomUUID().toString()) // 더미
                            .phoneNumber("000-0000-0000")
                            .oauthProvider(provider)
                            .oauthId(oauthId)
                            .roles(Set.of(USER))
                            .build();
                    return userInfoRepository.save(newUser);
                });

        // 5. 토큰 발급
        return this.makeToken(response, user);
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
