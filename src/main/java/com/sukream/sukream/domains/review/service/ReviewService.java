package com.sukream.sukream.domains.review.service;

import com.sukream.sukream.domains.bidder.entity.BidderStatus;
import com.sukream.sukream.domains.bidder.repository.BidderRepository;
import com.sukream.sukream.domains.product.entity.Product;
import com.sukream.sukream.domains.product.repository.ProductRepository;
import com.sukream.sukream.domains.review.domain.dto.CreateReviewRequest;
import com.sukream.sukream.domains.review.domain.dto.ReceivedReviewResponse;
import com.sukream.sukream.domains.review.domain.dto.ReceivedReviewSummaryResponse;
import com.sukream.sukream.domains.review.domain.entity.Review;
import com.sukream.sukream.domains.review.repository.ReviewRepository;

import com.sukream.sukream.domains.user.domain.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final BidderRepository bidderRepository;
    private final ProductRepository productRepository;

    @Transactional
    public void createReview(Users user, CreateReviewRequest request) {
        //1. 낙찰자인지 확인
        boolean isAwarded = bidderRepository.existsByUser_IdAndProduct_IdAndStatus(
                user.getId(), request.getProductId(), BidderStatus.AWARDED);

        if (!isAwarded) {
            throw new IllegalArgumentException("리뷰 작성 권한이 없습니다. ");
        }

        //2. 리뷰 중복 여부 확인
        boolean alreadyExists = reviewRepository.existsByWriterIdAndProductId(user.getId(), request.getProductId());
        if (alreadyExists) {
            throw new IllegalArgumentException("이미 리뷰를 작성한 상품입니다.");
        }

        //3. 리뷰 저장
        Product product = productRepository.findById(request.getProductId()).orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. "));

        Review review = Review.builder()
                .writer(user)
                .product(product)
                .rating(request.getRating())
                .content(request.getContent())
                .qualityAssessment(request.getQualityAssesment())
                .build();

        reviewRepository.save(review);
    }

    //판매자 본인 기준
    public ReceivedReviewSummaryResponse getReceivedReviews(Long sellerId) {
        return getReviewSummaryForUser(sellerId);
    }

    //특정 사용자 기준
    public ReceivedReviewSummaryResponse getReceivedReviewsByUserId(Long userId) {
        return getReviewSummaryForUser(userId);
    }

    //4. 리뷰 조회
    private ReceivedReviewSummaryResponse getReviewSummaryForUser(Long userId) {
        List<Review> reviews = reviewRepository.findByProduct_Owner_Id(userId);

        if (reviews.isEmpty()) {
            return ReceivedReviewSummaryResponse.builder()
                    .userName("")
                    .averageRating(0)
                    .reviews(Collections.emptyList())
                    .build();
        }

        String userName = reviews.get(0).getProduct().getOwner().getName();

        double averageRating = reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0);

        List<ReceivedReviewResponse> reviewResponses = reviews.stream()
                .map(review -> ReceivedReviewResponse.builder()
                        .productId(review.getProduct().getId())
                        .productName(review.getProduct().getTitle())
                        .rating(review.getRating())
                        .content(review.getContent())
                        .qualityAssesment(review.getQualityAssessment())
                        .build())
                .collect(Collectors.toList());

        return ReceivedReviewSummaryResponse.builder()
                .userName(userName)
                .averageRating(averageRating)
                .reviewCount(reviews.size())
                .reviews(reviewResponses)
                .build();
    }

}
