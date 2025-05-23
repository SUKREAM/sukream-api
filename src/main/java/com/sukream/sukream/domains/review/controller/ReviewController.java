package com.sukream.sukream.domains.review.controller;

import com.sukream.sukream.domains.review.domain.dto.CreateReviewRequest;
import com.sukream.sukream.domains.review.service.ReviewService;
import com.sukream.sukream.domains.user.domain.dto.UserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public void createReview(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody @Valid CreateReviewRequest request) {
        reviewService.createReview(userPrincipal.getUser(), request);
    }
}
