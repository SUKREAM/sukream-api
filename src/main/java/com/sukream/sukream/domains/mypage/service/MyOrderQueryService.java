package com.sukream.sukream.domains.mypage.service;

import com.sukream.sukream.domains.bidder.entity.Bidder;
import com.sukream.sukream.domains.bidder.entity.BidderStatus;
import com.sukream.sukream.domains.bidder.repository.BidderRepository;
import com.sukream.sukream.domains.mypage.domain.dto.OrderResponseDto;
import com.sukream.sukream.domains.user.domain.dto.UserPrincipal;
import com.sukream.sukream.domains.user.domain.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyOrderQueryService {
    private final BidderRepository bidderRepository;

    public List<OrderResponseDto> getMyOrders(Users user) {
        List<Bidder> allBids = bidderRepository.findAllByUser(user);
        List<OrderResponseDto> result = new ArrayList<>();

        for (Bidder bidder : allBids) {
            var product = bidder.getProduct();

            String status = bidder.getStatus() == BidderStatus.AWARDED ? "낙찰완료" : "낙찰대기";

            LocalDateTime orderDate = bidder.getStatus() == BidderStatus.AWARDED && bidder.getAwardedAt() != null
                    ? bidder.getAwardedAt()
                    : bidder.getBidAt();

            OrderResponseDto dto = OrderResponseDto.builder()
                    .orderId(bidder.getId())
                    .productId(product.getId())
                    .productName(product.getTitle())
                    .productImage(product.getImage())
                    .orderDate(orderDate)
                    .status(status)
                    .build();

            result.add(dto);
        }
        return result;
    }
}
