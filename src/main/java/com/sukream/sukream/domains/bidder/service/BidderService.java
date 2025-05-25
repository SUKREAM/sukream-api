package com.sukream.sukream.domains.bidder.service;

import com.sukream.sukream.domains.auth.repository.UserInfoRepository;
import com.sukream.sukream.domains.bidder.dto.request.BidRequest;
import com.sukream.sukream.domains.bidder.dto.response.AwardedBidderResponse;
import com.sukream.sukream.domains.bidder.dto.response.BidderResponse;
import com.sukream.sukream.domains.bidder.entity.Bidder;
import com.sukream.sukream.domains.bidder.entity.BidderStatus;
import com.sukream.sukream.domains.bidder.exception.*;
import com.sukream.sukream.domains.bidder.repository.BidderRepository;
import com.sukream.sukream.domains.product.entity.Product;
import com.sukream.sukream.domains.product.entity.ProductStatus;
import com.sukream.sukream.domains.product.repository.ProductRepository;
import com.sukream.sukream.domains.user.domain.entity.Users;
import jakarta.transaction.Transactional;
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
        List<Bidder> bidders = bidderRepository.findByProduct_IdOrderByPriceDesc(productId);

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

    @Transactional
    public Bidder placeBid(Long productId, BidRequest bidRequest, String userEmail) {
        // 상품 확인
        Product product = productRepository.findById(productId)
                .orElseThrow(BidInvalidProductException::new);

        // 사용자 확인
        Users user = userInfoRepository.findUsersByEmail(userEmail)
                .orElseThrow(UserNotFoundException::new);

        // 본인 상품 입찰 금지
        if (product.getOwner().getEmail().equals(userEmail)) {
            throw new BidSelfOwnProductException();
        }

        // 현재 최고 입찰가보다 낮으면 예외 발생
        Long highestBidPrice = bidderRepository.findHighestBidPriceByProductId(productId).orElse(0L);
        if (bidRequest.getPrice() <= highestBidPrice) {
            throw new BidAmountTooLowException();
        }

        // 입찰 마감 시간 지났는지 확인 (가정: product.getBidDeadline()이 LocalDateTime임)
        if (product.getBidDeadline() != null && product.getBidDeadline().isBefore(LocalDateTime.now())) {
            throw new BidDeadlineExceededException();
        }

        // 입찰 이미 했는지 확인 (예시)
        boolean alreadyBid = bidderRepository.existsByProductAndUser(product, user);
        if (alreadyBid) {
            throw new BidAlreadyPlacedException();
        }

        // product 경매 마감 or 낙찰 완료 시 입찰하면 예외 발생
        if (product.getStatus() == ProductStatus.CLOSED) {
            throw new BidDeadlineExceededException();
        }

        if (product.getStatus() == ProductStatus.AWARDED) {
            throw new BidAlreadyAwardedException();
        }

        // 가격 유효성 검사 (예: 0 이하 금지)
        if (bidRequest.getPrice() <= 0) {
            throw new BidInvalidAmountException();
        }

        // 입찰 생성
        Bidder bidder = Bidder.builder()
                .product(product)
                .user(user)
                .price(bidRequest.getPrice())
                .status(BidderStatus.PENDING)
                .isAwarded(false)
                .bidAt(LocalDateTime.now())
                .nickname(bidRequest.getNickname())
                .build();

        return bidderRepository.save(bidder);
    }

    @Transactional
    public Bidder awardBidder(Long productId, Long bidderId, String userEmail) {
        Product product = productRepository.findById(productId)
                .orElseThrow(BidInvalidProductException::new);

        if (!product.getOwner().getEmail().equals(userEmail)) {
            throw new UnauthorizedAwardAccessException();
        }

        Bidder bidder = bidderRepository.findById(bidderId)
                .orElseThrow(BidderNotFoundException::new);

        if (!bidder.getProduct().getId().equals(productId)) {
            throw new BidderNotBelongToProductException();
        }

        bidder.award();
        product.awardAuction(); // product status 변경
        return bidder;
    }

    public AwardedBidderResponse toAwardedResponse(Bidder bidder) {
        return AwardedBidderResponse.builder()
                .bidderId(bidder.getId())
                .nickname(bidder.getNickname())
                .price(bidder.getPrice())
                .productId(bidder.getProduct().getId())
                .awardedAt(bidder.getAwardedAt())
                .build();
    }

}

