package com.sukream.sukream.domains.review.controller;

import com.sukream.sukream.domains.review.domain.dto.CreateReviewRequest;
import com.sukream.sukream.domains.review.service.ReviewService;
import com.sukream.sukream.domains.user.domain.dto.UserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private static final Logger log = LoggerFactory.getLogger(ReviewController.class);
    private final ReviewService reviewService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createReview(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody @Valid CreateReviewRequest request) {
        reviewService.createReview(userPrincipal.getUser(), request);
    }
}
