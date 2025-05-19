package com.sukream.sukream.domains.bidder.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AwardBidderResponse {

    private String message;
    private AwardedBidderInfo awardedBidder;

    @Getter
    @Builder
    @AllArgsConstructor
    public static class AwardedBidderInfo {
        private Long bidderId;
        private String nickname;
        private Integer price;
        private Long productId;
    }
}
