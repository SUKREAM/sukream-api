package com.sukream.sukream.domains.review.controller;

import com.sukream.sukream.domains.review.domain.dto.CreateReviewRequest;
import com.sukream.sukream.domains.review.domain.dto.ReceivedReviewSummaryResponse;
import com.sukream.sukream.domains.review.service.ReviewService;
import com.sukream.sukream.domains.user.domain.dto.UserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createReview(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody @Valid CreateReviewRequest request) {
        reviewService.createReview(userPrincipal.getUser(), request);
    }


    @GetMapping("/me/received")
    public ReceivedReviewSummaryResponse getReceivedReviews(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return reviewService.getReceivedReviews(userPrincipal.getUser().getId());
    }


    @GetMapping("/users/{userId}/reviews")
    public ReceivedReviewSummaryResponse getUserReceivedReviews(@PathVariable("userId") Long userId) {
        return reviewService.getReceivedReviewsByUserId(userId);
    }
}
