package com.sukream.sukream.domains.review.service;

import com.sukream.sukream.domains.bidder.repository.BidderRepository;
import com.sukream.sukream.domains.product.entity.Product;
import com.sukream.sukream.domains.product.repository.ProductRepository;
import com.sukream.sukream.domains.review.domain.dto.CreateReviewRequest;
import com.sukream.sukream.domains.review.domain.entity.Review;
import com.sukream.sukream.domains.review.repository.ReviewRepository;
import com.sukream.sukream.domains.user.domain.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final BidderRepository bidderRepository;
    private final ProductRepository productRepository;

    @Transactional
    public void createReview(Users user, CreateReviewRequest request) {
        //1. 낙찰자인지 확인
        boolean isAwarded = bidderRepository.existsByUser_IdAndProduct_ProductIdAndIsAwardedTrue(user.getId(), request.getProductId());

        if(!isAwarded) {
            throw new IllegalArgumentException("리뷰 작성 권한이 없습니다. ");
        }

        //2. 리뷰 중복 여부 확인
        boolean alreadyExists = reviewRepository.existsByWriterIdAndProductProductId(user.getId(), request.getProductId());
        if(!alreadyExists) {
            throw new IllegalArgumentException("이미 리뷰를 작성한 상품입니다.");
        }

        //3. 리뷰 저장
        Product product = productRepository.findById(request.getProductId()).orElseThrow(()->new IllegalArgumentException("상품을 찾을 수 없습니다. "));

        Review review = Review.builder()
                .writer(user)
                .product(product)
                .rating(request.getRating())
                .content(request.getContent())
                .qualityAssessment(request.getQualityAssesment())
                .build();

        reviewRepository.save(review);
    }
}
