package com.sukream.sukream.domains.product.dto;

import com.sukream.sukream.domains.product.entity.Product;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Base64;

@Getter
@Builder
public class ProductResponse {
    private Long id;
    private Long sellerId;
    private String sellerName;    // 추가된 필드
    private String title;
    private String description;
    private int minPrice;
    private int maxPrice;
    private String category;
    private int bidUnit;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deadline;
    private String image;
    private String chatLink;
    private String status;
    private String auctionNum;
    private int bidCount;

    public static ProductResponse fromEntityAndBidCount(Product product, int bidCount) {
        return ProductResponse.builder()
                .id(product.getId())
                .sellerId(product.getOwner().getId())
                .sellerName(product.getOwner().getName())  // 추가
                .title(product.getTitle())
                .description(product.getDescription())
                .minPrice(product.getMinPrice())
                .maxPrice(product.getMaxPrice())
                .category(product.getCategory())
                .bidUnit(product.getBidUnit())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .deadline(product.getDeadline())
                .image(product.getImage() != null ? Base64.getEncoder().encodeToString(product.getImage()) : null)
                .chatLink(product.getChatLink())
                .status(product.getStatus().getDescription())
                .auctionNum(product.getAuctionNum())
                .bidCount(bidCount)
                .build();
    }
}
