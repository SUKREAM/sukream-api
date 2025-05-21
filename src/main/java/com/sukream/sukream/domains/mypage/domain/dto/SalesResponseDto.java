package com.sukream.sukream.domains.mypage.domain.dto;

import com.sukream.sukream.domains.product.entity.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class SalesResponseDto {
    private Long productId;
    private String productName;
    private String productImage;
    private LocalDateTime createdAt; //판매시간
    private ProductStatus status;
}
