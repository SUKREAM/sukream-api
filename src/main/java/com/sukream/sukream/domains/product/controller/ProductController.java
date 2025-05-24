package com.sukream.sukream.domains.product.controller;

import com.sukream.sukream.commons.constants.ErrorCode;
import com.sukream.sukream.commons.constants.SuccessCode;
import com.sukream.sukream.commons.domain.response.DataResponse;
import com.sukream.sukream.commons.domain.response.Response;
import com.sukream.sukream.domains.product.dto.AddProductRequest;
import com.sukream.sukream.domains.product.dto.ProductResponse;
import com.sukream.sukream.domains.product.dto.UpdateProductRequest;
import com.sukream.sukream.domains.product.service.ProductService;
import com.sukream.sukream.domains.user.domain.dto.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    // 상품 등록
    @Operation(summary = "상품 등록", description = "경매 상품을 등록한다.")
    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody AddProductRequest requestDto,
                                           @AuthenticationPrincipal UserPrincipal userPrincipal) {
        try {
            requestDto.setSellerId(userPrincipal.getUser().getId());  // 로그인한 유저 ID로 강제 설정

            Long productId = productService.createProduct(requestDto);
            return ResponseEntity.ok(DataResponse.success(productId, SuccessCode.PRODUCT_CREATE_SUCCESS));
        } catch (IllegalArgumentException e) {
            return Response.toErrorResponseEntity(ErrorCode.ERR_UNKNOWN.getValue(), e.getMessage());
        } catch (Exception e) {
            return Response.toErrorResponseEntity(ErrorCode.ERR_UNKNOWN.getValue(), ErrorCode.ERR_UNKNOWN.getDescription());
        }
    }

    // 상품 단건 조회
    @Operation(summary = "상품 상세 조회", description = "상품을 id로 상세 조회한다.")
    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id) {
        try {
            ProductResponse responseDto = productService.getProduct(id);
            return ResponseEntity.ok(DataResponse.success(responseDto, SuccessCode.PRODUCT_READ_SUCCESS));
        } catch (EntityNotFoundException e) {
            return Response.toErrorResponseEntity(ErrorCode.RESOURCE_NOT_FOUND.getValue(), ErrorCode.RESOURCE_NOT_FOUND.getDescription());
        } catch (Exception e) {
            return Response.toErrorResponseEntity(ErrorCode.ERR_UNKNOWN.getValue(), ErrorCode.ERR_UNKNOWN.getDescription());
        }
    }

    // 상품 전체 조회
    @Operation(summary = "상품 목록 조회", description = "전체 상품 리스트를 조회한다.")
    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        try {
            List<ProductResponse> products = productService.getAllProducts();
            return ResponseEntity.ok(DataResponse.success(products, SuccessCode.PRODUCT_READ_SUCCESS));
        } catch (Exception e) {
            return Response.toErrorResponseEntity(ErrorCode.ERR_UNKNOWN.getValue(), ErrorCode.ERR_UNKNOWN.getDescription());
        }
    }

    // 상품 수정
    @Operation(summary = "상품 수정", description = "title, description, minPrice, maxPrice, category, bidUnit, deadline, image, chatLink를 수정한다.")
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id,
                                           @RequestBody UpdateProductRequest requestDto,
                                           @AuthenticationPrincipal UserPrincipal userPrincipal) {
        try {
            // 로그인한 사용자가 해당 상품의 소유자인지 검증
            productService.validateProductOwner(id, userPrincipal.getUser().getId());

            productService.updateProduct(id, requestDto);
            return ResponseEntity.ok(DataResponse.success(null, SuccessCode.PRODUCT_UPDATE_SUCCESS));
        } catch (EntityNotFoundException e) {
            return Response.toErrorResponseEntity(ErrorCode.RESOURCE_NOT_FOUND.getValue(), ErrorCode.RESOURCE_NOT_FOUND.getDescription());
        } catch (IllegalArgumentException e) {
            return Response.toErrorResponseEntity(ErrorCode.ERR_UNKNOWN.getValue(), e.getMessage());
        } catch (Exception e) {
            return Response.toErrorResponseEntity(ErrorCode.ERR_UNKNOWN.getValue(), ErrorCode.ERR_UNKNOWN.getDescription());
        }
    }

    // 상품 삭제
    @Operation(summary = "상품 삭제", description = "상품을 삭제한다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id,
                                           @AuthenticationPrincipal UserPrincipal userPrincipal)  {
        try {
            // 로그인한 사용자가 해당 상품의 소유자인지 검증
            productService.validateProductOwner(id, userPrincipal.getUser().getId());

            productService.deleteProduct(id);
            return ResponseEntity.ok(DataResponse.success(null, SuccessCode.PRODUCT_DELETE_SUCCESS));
        } catch (EntityNotFoundException e) {
            return Response.toErrorResponseEntity(ErrorCode.RESOURCE_NOT_FOUND.getValue(), ErrorCode.RESOURCE_NOT_FOUND.getDescription());
        } catch (Exception e) {
            return Response.toErrorResponseEntity(ErrorCode.ERR_UNKNOWN.getValue(), ErrorCode.ERR_UNKNOWN.getDescription());
        }
    }
}
