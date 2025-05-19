package com.sukream.sukream.domains.bidder.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class BidderResponse {
    private Long bidderId;
    private String nickname;
    private Integer price;
    private String submittedAgo;
}
