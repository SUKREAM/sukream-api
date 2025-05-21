package com.sukream.sukream.domains.bidder.controller;

import com.sukream.sukream.commons.constants.ErrorCode;
import com.sukream.sukream.commons.constants.SuccessCode;
import com.sukream.sukream.commons.domain.response.DataResponse;
import com.sukream.sukream.commons.domain.response.Response;
import com.sukream.sukream.domains.bidder.dto.request.BidRequest;
import com.sukream.sukream.domains.bidder.dto.response.BidderResponse;
import com.sukream.sukream.domains.bidder.entity.Bidder;
import com.sukream.sukream.domains.bidder.service.BidderService;
import com.sukream.sukream.domains.user.domain.dto.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "입찰자 목록 조회", description = "상품에 입찰한 모든 입찰자를 조회한다.")
    @GetMapping
    public ResponseEntity<?> getBidders(@PathVariable Long productId,
                                        @AuthenticationPrincipal UserPrincipal userPrincipal) {
        String userEmail = userPrincipal.getUsername();

        if (!bidderService.isProductOwner(productId, userEmail)) {
            return Response.toErrorResponseEntity(ErrorCode.FORBIDDEN);
        }

        List<BidderResponse> bidders = bidderService.getBiddersByProductId(productId);
        return ResponseEntity.ok(DataResponse.success(bidders, SuccessCode.BIDDER_READ_SUCCESS));
    }

    @Operation(summary = "입찰하기", description = "소비자가 상품에 대해 입찰한다.")
    @PostMapping
    public ResponseEntity<?> placeBid(@PathVariable Long productId,
                                      @RequestBody BidRequest bidRequest,
                                      @AuthenticationPrincipal UserPrincipal userPrincipal) {
        String userEmail = userPrincipal.getUsername();

        // 예외는 BidderService 내부에서 처리
        Bidder savedBid = bidderService.placeBid(productId, bidRequest, userEmail);
        BidderResponse response = bidderService.toBidderResponse(savedBid);

        return new ResponseEntity<>(DataResponse.success(response, SuccessCode.BIDDER_CREATE_SUCCESS),
                HttpStatus.CREATED);
    }
}
