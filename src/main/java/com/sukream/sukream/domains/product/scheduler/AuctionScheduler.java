package com.sukream.sukream.domains.product.scheduler;

import com.sukream.sukream.domains.bidder.service.BidderService;
import com.sukream.sukream.domains.product.entity.Product;
import com.sukream.sukream.domains.product.entity.ProductStatus;
import com.sukream.sukream.domains.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AuctionScheduler {

    private final ProductRepository productRepository;
    private final BidderService bidderService;

    @Scheduled(fixedRate = 60000) // 1분마다 실행
    public void autoAwardExpiredAuctions() {
        List<Product> expiredProducts = productRepository.findAll().stream()
                .filter(product ->
                        product.getStatus() == ProductStatus.OPEN &&
                                product.getBidDeadline() != null &&
                                product.getBidDeadline().isBefore(LocalDateTime.now()))
                .toList();

        for (Product product : expiredProducts) {
            bidderService.awardHighestBidder(product.getId());
        }
    }
}