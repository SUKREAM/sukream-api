package com.sukream.sukream.domains.product.dto;

import com.sukream.sukream.domains.product.entity.Product;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ProductResponse {
    private Long productId;
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
    private String status;
    private String auctionNum;
    private int bidAccount;

    public static ProductResponse fromEntity(Product product) {
        return ProductResponse.builder()
                .productId(product.getProductId())
                .sellerId(product.getSellerId())
                .title(product.getTitle())
                .description(product.getDescription())
                .minPrice(product.getMinPrice())
                .maxPrice(product.getMaxPrice())
                .category(product.getCategory())
                .bidUnit(product.getBidUnit())
                .deadline(product.getDeadline())
                .image(product.getImage())
                .chatLink(product.getChatLink())
                .status(product.getStatus())
                .auctionNum(product.getAuctionNum())
                .bidAccount(product.getBidAccount())
                .build();
    }
}