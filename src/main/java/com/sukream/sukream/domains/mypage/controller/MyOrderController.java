package com.sukream.sukream.domains.mypage.controller;

import com.sukream.sukream.domains.mypage.domain.dto.OrderResponseDto;
import com.sukream.sukream.domains.mypage.service.MyOrderQueryService;
import com.sukream.sukream.domains.user.domain.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/mypage/orders")
public class MyOrderController {
    private final MyOrderQueryService myOrderQueryService;
    @GetMapping
    public List<OrderResponseDto> getMyOrders(@RequestAttribute("user") Users user){
        return myOrderQueryService.getMyOrders(user);
    }

}
