package com.sukream.sukream.domains.product.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AddProductRequest {
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
