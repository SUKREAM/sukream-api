package com.sukream.sukream.domains.review.domain.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ReceivedReviewSummaryResponse {
    private String userName;
    private double averageRating;
    private List<ReceivedReviewResponse> reviews;
}
