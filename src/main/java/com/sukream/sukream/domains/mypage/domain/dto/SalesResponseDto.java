package com.sukream.sukream.domains.mypage.domain.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class SalesResponseDto {
    private Long productId;
    private String productName;
    private String productImage;
    private LocalDateTime createdAt; //판매시간
    private String status; //판매중 / 판매완료
}
