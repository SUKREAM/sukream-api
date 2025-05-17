package com.sukream.sukream.domains.bidder.dto.response;

import lombok.Getter;
import lombok.Setter;

public class AwardBidderResponse {

    private String message;
    private AwardedBidderInfo awardedBidder;

    @Getter
    @Setter
    public static class AwardedBidderInfo {
        private Long bidderId;
        private String nickname;
        private Integer price;
        private Long productId;
    }
}
