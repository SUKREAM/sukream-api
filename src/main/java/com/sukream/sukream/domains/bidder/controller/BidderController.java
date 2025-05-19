package com.sukream.sukream.domains.bidder.controller;

import com.sukream.sukream.domains.bidder.dto.response.BidderResponse;
import com.sukream.sukream.domains.bidder.service.BidderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products/{productId}/bidders")
public class BidderController {

    private final BidderService bidderService;

    @GetMapping
    public ResponseEntity<List<BidderResponse>> getBidders(@PathVariable Long productId) {
        List<BidderResponse> bidders = bidderService.getBiddersByProductId(productId);
        return ResponseEntity.ok(bidders);
    }
}
