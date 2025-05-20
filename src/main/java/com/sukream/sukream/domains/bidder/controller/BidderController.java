package com.sukream.sukream.domains.bidder.controller;

import com.sukream.sukream.domains.bidder.dto.request.BidRequest;
import com.sukream.sukream.domains.bidder.dto.response.AwardBidderResponse;
import com.sukream.sukream.domains.bidder.dto.response.BidderResponse;
import com.sukream.sukream.domains.bidder.entity.Bidder;
import com.sukream.sukream.domains.bidder.service.BidderService;
import com.sukream.sukream.domains.user.domain.dto.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products/{productId}/bidders")
public class BidderController {

    private final BidderService bidderService;

    @GetMapping
    public ResponseEntity<List<BidderResponse>> getBidders(@PathVariable Long productId, @AuthenticationPrincipal UserPrincipal userprincipal) {

        // 로그인한 사용자 이메일
        String userEmail = userprincipal.getUsername();

        // 본인 상품인지 확인
        if (!bidderService.isProductOwner(productId, userEmail)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        List<BidderResponse> bidders = bidderService.getBiddersByProductId(productId);
        return ResponseEntity.ok(bidders);
    }

    @PostMapping
    public ResponseEntity<BidderResponse> placeBid(@PathVariable Long productId,
                                                   @RequestBody BidRequest bidRequest,
                                                   @AuthenticationPrincipal UserPrincipal userPrincipal) {
        String userEmail = userPrincipal.getUsername();

        Bidder savedBid = bidderService.placeBid(productId, bidRequest, userEmail);
        BidderResponse response = bidderService.toBidderResponse(savedBid);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
