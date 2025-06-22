package com.sukream.sukream.domains.user.controller;

import com.sukream.sukream.domains.auth.domain.request.SignInRequest;
import com.sukream.sukream.domains.auth.domain.response.TokenResponse;
import com.sukream.sukream.domains.user.domain.request.UserRequest;
import com.sukream.sukream.domains.user.domain.response.UserResponse;
import com.sukream.sukream.domains.user.service.MyPageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/mypage")
@Tag(name = "MyPage", description = "회원정보 관련 컨트롤러")

public class MyPageController {

    private final MyPageService myPageService;

    /**
     * 내 정보 수정 Controller
     */
    @Operation(summary = "마이페이지", description = """
             마이페이지 정보를 변경 한다.
            """)
    @PostMapping("/update")
    public UserResponse myPage(@RequestBody UserRequest userRequest) {
        return myPageService.myPage(userRequest);
    }

    @Operation(summary = "마이페이지", description = """
             마이페이지 사용자 정보 가져오기.
            """)
    @PostMapping("/user-info")
    public UserResponse userInfo() {
        return myPageService.getUserInfo();
    }


}
