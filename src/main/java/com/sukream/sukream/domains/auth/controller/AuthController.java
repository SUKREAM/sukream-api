package com.sukream.sukream.domains.auth.controller;

import com.sukream.sukream.commons.domain.response.Response;
import com.sukream.sukream.domains.auth.domain.request.LoginRequest;
import com.sukream.sukream.domains.auth.domain.response.TokenResponse;
import com.sukream.sukream.domains.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/auth")
@Tag(name = "Auth", description = "회원가입, 로그인 관련 컨트롤러")
/**
 * 회원가입, 로그인 관련 Controller
 */
public class AuthController {
    private final AuthService authService;

    /**
     * 테스트용 - 로그인 Controller
     */
    @Operation(summary = "테스트용 - 로그인", description = """
             아이디, 비밀번호로 로그인 한다.
            """)
    @PostMapping("/login")
    public TokenResponse doLogin(HttpServletResponse response, LoginRequest loginRequest) {
        return authService.login(response, loginRequest);
    }

    /**
     * oAuth2 로그인 사용자 검증 Controller
     */
    @Operation(summary = "oAuth2 로그인 사용자 검증", description = """
             oAuth2 accessToken 으로 회원가입한 사용자인지 검증한다.
             회원가입한 사용자일 시 accessToken, refreshToken 발급
             아닐 시 "Resource Not Found(404) 에러 전송
             provider - google, naver
            """)
    @PostMapping("/verification/{provider}")
    public Response doVerification(HttpServletResponse response, @PathVariable("provider") String provider, @RequestParam(value = "code") String accessToken){
        return authService.doVerification(response, provider, accessToken);
    }
}
