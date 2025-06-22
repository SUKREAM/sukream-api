package com.sukream.sukream.domains.bidder.controller;

import com.sukream.sukream.commons.constants.ErrorCode;
import com.sukream.sukream.commons.constants.SuccessCode;
import com.sukream.sukream.commons.domain.response.DataResponse;
import com.sukream.sukream.commons.domain.response.Response;
import com.sukream.sukream.domains.bidder.dto.request.BidRequest;
import com.sukream.sukream.domains.bidder.dto.response.BidderResponse;
import com.sukream.sukream.domains.bidder.entity.Bidder;
import com.sukream.sukream.domains.bidder.exception.*;
import com.sukream.sukream.domains.bidder.service.BidderService;
import com.sukream.sukream.domains.product.dto.AuctionProductInfoResponse;
import com.sukream.sukream.domains.product.service.ProductService;
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
    private final ProductService productService;

    @GetMapping("/info")
    public ResponseEntity<?> getAuctionProductInfo(@PathVariable("productId") Long productId) {
        AuctionProductInfoResponse response = productService.getAuctionProductInfo(productId);
        return ResponseEntity.ok(DataResponse.success(response, SuccessCode.PRODUCT_READ_SUCCESS));
    }

    @Operation(summary = "입찰자 목록 조회", description = "상품에 입찰한 모든 입찰자를 조회한다.")
    @GetMapping
    public ResponseEntity<?> getBidders(@PathVariable("productId") Long productId,
                                        @AuthenticationPrincipal UserPrincipal userPrincipal) {
        try {
            String userEmail = userPrincipal.getUsername();

            if (!bidderService.isProductOwner(productId, userEmail)) {
                return Response.toErrorResponseEntity(ErrorCode.FORBIDDEN);
            }

            List<BidderResponse> bidders = bidderService.getBiddersByProductId(productId);
            return ResponseEntity.ok(DataResponse.success(bidders, SuccessCode.BIDDER_READ_SUCCESS));
        } catch (Exception e) {
            return Response.toErrorResponseEntity(ErrorCode.ERR_UNKNOWN.getValue(), ErrorCode.ERR_UNKNOWN.getDescription());
        }
    }

    @Operation(summary = "입찰하기", description = "소비자가 상품에 대해 입찰한다.")
    @PostMapping
    public ResponseEntity<?> placeBid(@PathVariable("productId") Long productId,
                                      @RequestBody BidRequest bidRequest,
                                      @AuthenticationPrincipal UserPrincipal userPrincipal) {
        try {
            String userEmail = userPrincipal.getUsername();

            Bidder savedBid = bidderService.placeBid(productId, bidRequest, userEmail);
            BidderResponse response = bidderService.toBidderResponse(savedBid);

            return new ResponseEntity<>(DataResponse.success(response, SuccessCode.BIDDER_CREATE_SUCCESS),
                    HttpStatus.CREATED);
        } catch (BidSelfOwnProductException e) {
            return Response.toErrorResponseEntity(ErrorCode.BID_SELF_OWN_PRODUCT);
        } catch (BidAmountTooLowException e) {
            return Response.toErrorResponseEntity(ErrorCode.BID_AMOUNT_TOO_LOW);
        } catch (BidAlreadyPlacedException e) {
            return Response.toErrorResponseEntity(ErrorCode.BID_ALREADY_PLACED);
        } catch (BidInvalidProductException e) {
            return Response.toErrorResponseEntity(ErrorCode.BID_INVALID_PRODUCT);
        } catch (BidInvalidAmountException e) {
            return Response.toErrorResponseEntity(ErrorCode.BID_INVALID_AMOUNT);
        } catch (BidDeadlineExceededException e) {
            return Response.toErrorResponseEntity(ErrorCode.BID_DEADLINE_EXCEEDED);
        } catch (UserNotFoundException e) {
            return Response.toErrorResponseEntity(ErrorCode.USER_NOT_FOUND);
        } catch (Exception e) {
            return Response.toErrorResponseEntity(ErrorCode.ERR_UNKNOWN.getValue(), ErrorCode.ERR_UNKNOWN.getDescription());
        }
    }

    @Operation(summary = "낙찰자 지정", description = "판매자가 특정 입찰자를 낙찰자로 지정한다.")
    @PostMapping("/award/{bidderId}")
    public ResponseEntity<?> awardBidder(@PathVariable("productId") Long productId,
                                         @PathVariable("bidderId") Long bidderId,
                                         @AuthenticationPrincipal UserPrincipal userPrincipal) {
        try {
            String userEmail = userPrincipal.getUsername();
            Bidder awardedBidder = bidderService.awardBidder(productId, bidderId, userEmail);
            return ResponseEntity.ok(DataResponse.success(bidderService.toAwardedResponse(awardedBidder),
                    SuccessCode.BIDDER_AWARD_SUCCESS));
        } catch (BidderNotFoundException e) {
            return Response.toErrorResponseEntity(ErrorCode.BIDDER_NOT_FOUND);
        } catch (BidderNotBelongToProductException e) {
            return Response.toErrorResponseEntity(ErrorCode.BIDDER_NOT_BELONG_TO_PRODUCT);
        } catch (UnauthorizedAwardAccessException e) {
            return Response.toErrorResponseEntity(ErrorCode.FORBIDDEN);
        } catch (Exception e) {
            return Response.toErrorResponseEntity(ErrorCode.ERR_UNKNOWN.getValue(), ErrorCode.ERR_UNKNOWN.getDescription());
        }
    }

}
