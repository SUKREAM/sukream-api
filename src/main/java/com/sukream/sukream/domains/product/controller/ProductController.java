package com.sukream.sukream.domains.product.controller;

import com.sukream.sukream.domains.product.dto.AddProductRequest;
import com.sukream.sukream.domains.product.dto.ProductResponse;
import com.sukream.sukream.domains.product.dto.UpdateProductRequest;
import com.sukream.sukream.domains.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    // 상품 등록
    @Operation(summary = "상품 등록", description = """
             경매 상품을 등록한다.
            """)
    @PostMapping
    public ResponseEntity<Long> createProduct(@RequestBody AddProductRequest requestDto) {
        Long productId = productService.createProduct(requestDto);
        return ResponseEntity.ok(productId);
    }


    // 상품 단건 조회
    @Operation(summary = "상품 상세 조회", description = """
             상품을 id로 상세 조회한다.
            """)
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long id) {
        ProductResponse responseDto = productService.getProduct(id);
        return ResponseEntity.ok(responseDto);
    }

    // 상품 전체 조회
    @Operation(summary = "상품 목록 조회", description = """
             전체 상품 리스트를 조회한다.
            """)
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<ProductResponse> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    // 상품 수정
    @Operation(summary = "상품 수정", description = """
         title, description, minPrice, maxPrice, category, bidUnit, deadline, image, chatLink를 수정한다.
         """)
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable Long id,
                                              @RequestBody UpdateProductRequest requestDto) {
        productService.updateProduct(id, requestDto);
        return ResponseEntity.ok().build();
    }

    // 상품 삭제
    @Operation(summary = "상품 삭제", description = """
           상품을 삭제한다.
           """)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
