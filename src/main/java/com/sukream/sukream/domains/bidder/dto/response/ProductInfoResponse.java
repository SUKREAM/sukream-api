package com.sukream.sukream.domains.bidder.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductInfoResponse {
    private Long productId;
    private String title;
    private String description;
    private String timeRemaining;
    private int highestBid;
    private String endTime;
}
