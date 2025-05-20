package com.sukream.sukream.domains.mypage.controller;


import com.sukream.sukream.domains.mypage.domain.dto.SalesResponseDto;
import com.sukream.sukream.domains.mypage.service.MySalesQueryService;
import com.sukream.sukream.domains.user.domain.dto.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage/sales")
public class MySalesController {
    private final MySalesQueryService mySalesQueryService;
    @GetMapping
    public List<SalesResponseDto> getMySales(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return mySalesQueryService.getMySales(userPrincipal.getUser());
    }
}
