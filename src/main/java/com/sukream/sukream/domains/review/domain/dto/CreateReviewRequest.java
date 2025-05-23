package com.sukream.sukream.domains.review.domain.dto;

import com.sukream.sukream.domains.review.domain.entity.QualityAssesment;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateReviewRequest {
    @NotNull
    private Long productId;

    @Min(1)
    @Max(5)
    private int rating;

    @NotBlank
    private String content;

    @NotNull
    private QualityAssesment qualityAssesment;
}
