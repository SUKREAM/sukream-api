package com.sukream.sukream.domains.review.domain.dto;

import com.sukream.sukream.domains.review.domain.entity.QualityAssesment;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReceivedReviewResponse {
    private Long productId;
    private String productName;
    private int rating;
    private String content;
    private QualityAssesment qualityAssesment;
}
