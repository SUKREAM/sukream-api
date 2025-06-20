package com.sukream.sukream.domains.review.domain.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ReceivedReviewSummaryResponse {
    private final String userName;
    private final double averageRating;
    private final int reviewCount;
    private final List<ReceivedReviewResponse> reviews;

    @Builder
    public ReceivedReviewSummaryResponse(String userName, double averageRating, int reviewCount, List<ReceivedReviewResponse> reviews) {
        this.userName = userName;
        this.averageRating = averageRating;
        this.reviewCount = reviewCount;
        this.reviews = reviews;
    }
}
