package com.sukream.sukream.domains.mypage.domain.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class OrderResponseDto {
    private Long orderId; //입찰 ID
    private Long productId; //상품 ID
    private String productName; //상품명
    private String productImage; //상품이미지
    private LocalDateTime orderDate; //(소비자 기준) 입찰일 or 낙찰일
    private String status; //낙찰완료 or 낙찰대기
}
