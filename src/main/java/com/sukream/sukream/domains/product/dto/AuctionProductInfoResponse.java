package com.sukream.sukream.domains.product.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuctionProductInfoResponse {
    private Long productId;
    private String title;
    private String description;
    private String timeRemaining;
    private int highestBid;
    private String endTime;
}