package com.sukream.sukream.domains.user.controller;

import com.sukream.sukream.domains.auth.domain.request.SignInRequest;
import com.sukream.sukream.domains.auth.domain.response.TokenResponse;
import com.sukream.sukream.domains.user.domain.request.UserRequest;
import com.sukream.sukream.domains.user.domain.response.UserResponse;
import com.sukream.sukream.domains.user.service.MyPageService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    /**
     * 내 정보 수정 Controller
     */
    @Operation(summary = "마이페이지", description = """
             마이페이지 정보를 변경 한다.
            """)
    @PostMapping("/mypage")
    public UserResponse myPage(UserRequest userRequest) {
        return myPageService.myPage(userRequest);
    }


}
