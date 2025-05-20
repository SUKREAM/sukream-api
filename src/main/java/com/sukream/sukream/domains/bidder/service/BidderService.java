package com.sukream.sukream.domains.bidder.service;

import com.sukream.sukream.domains.bidder.dto.response.BidderResponse;
import com.sukream.sukream.domains.bidder.entity.Bidder;
import com.sukream.sukream.domains.bidder.repository.BidderRepository;
import com.sukream.sukream.domains.product.repository.ProductRepository;
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

    public List<BidderResponse> getBiddersByProductId(Long productId) {
        List<Bidder> bidders = bidderRepository.findByProduct_ProductIdOrderByPriceDesc(productId);

        return bidders.stream()
                .map(this::toBidderResponse)
                .collect(Collectors.toList());
    }

    private BidderResponse toBidderResponse(Bidder bidder) {
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
}

