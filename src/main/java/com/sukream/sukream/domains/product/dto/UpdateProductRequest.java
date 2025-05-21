package com.sukream.sukream.domains.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateProductRequest {
    private String title;
    private String description;
    private Integer minPrice;
    private Integer maxPrice;
    private String category;
    private Integer bidUnit;
    private LocalDateTime deadline;
    private String image;
    private String chatLink;
    private String status;
}