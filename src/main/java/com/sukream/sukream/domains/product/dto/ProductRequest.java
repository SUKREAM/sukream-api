package com.sukream.sukream.domains.product.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor // 기본 생성자 추가
@Getter
public class ProductRequest {
    private Long sellerId;
    private String title;
    private String description;
    private int minPrice;
    private int maxPrice;
    private String category;
    private int bidUnit;
    private LocalDateTime deadline;
    private String image;
    private String chatLink;
}
