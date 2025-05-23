package com.sukream.sukream.domains.bidder.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AwardedBidderResponse {
    private Long bidderId;
    private String nickname;
    private Integer price;
    private Long productId;
    private LocalDateTime awardedAt;

}
