package com.sukream.sukream.domains.bidder.service;

import com.sukream.sukream.domains.auth.repository.UserInfoRepository;
import com.sukream.sukream.domains.bidder.dto.request.BidRequest;
import com.sukream.sukream.domains.bidder.dto.response.AwardBidderResponse;
import com.sukream.sukream.domains.bidder.dto.response.BidderResponse;
import com.sukream.sukream.domains.bidder.entity.Bidder;
import com.sukream.sukream.domains.bidder.repository.BidderRepository;
import com.sukream.sukream.domains.product.entity.Product;
import com.sukream.sukream.domains.product.repository.ProductRepository;
import com.sukream.sukream.domains.user.domain.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BidderService {

    private final BidderRepository bidderRepository;
    private final ProductRepository productRepository;
    private final UserInfoRepository userInfoRepository;

    public List<BidderResponse> getBiddersByProductId(Long productId) {
        List<Bidder> bidders = bidderRepository.findByProduct_ProductIdOrderByPriceDesc(productId);

        return bidders.stream()
                .map(this::toBidderResponse)
                .collect(Collectors.toList());
    }

    public BidderResponse toBidderResponse(Bidder bidder) {
        String submittedAgo = calculateTimeAgo(bidder.getBidAt());
        return BidderResponse.builder()
                .bidderId(bidder.getId())
                .nickname(bidder.getNickname())
                .price(bidder.getPrice())
                .submittedAgo(submittedAgo)
                .build();
    }

    private String calculateTimeAgo(LocalDateTime createdAt) {
        if (createdAt == null) {
            return "시간 정보 없음";
        }
        Duration duration = Duration.between(createdAt, LocalDateTime.now());

        long seconds = duration.getSeconds();
        if (seconds < 60) {
            return seconds + "초 전";
        } else if (seconds < 3600) {
            return (seconds / 60) + "분 전";
        } else if (seconds < 86400) {
            return (seconds / 3600) + "시간 전";
        } else {
            return (seconds / 86400) + "일 전";
        }
    }

    // 본인 상품인지 확인
    public boolean isProductOwner(Long productId, String userEmail) {
        return productRepository.findById(productId)
                .map(product -> product.getOwner().getEmail().equals(userEmail))
                .orElse(false);
    }

    public Bidder placeBid(Long productId, BidRequest bidRequest, String userEmail) {
        // 상품 확인
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));

        // 사용자 확인
        Users user = userInfoRepository.findUsersByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));

        // 입찰 생성
        Bidder bidder = Bidder.builder()
                .product(product)
                .user(user)
                .price(bidRequest.getPrice())
                .nickname(bidRequest.getNickname())
                .status(null)
                .isAwarded(null)
                .bidAt(null)
                .build();

        return bidderRepository.save(bidder);
    }

}

