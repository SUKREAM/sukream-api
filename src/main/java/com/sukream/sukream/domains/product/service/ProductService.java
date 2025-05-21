package com.sukream.sukream.domains.product.service;

import com.sukream.sukream.domains.auth.repository.UserInfoRepository;
import com.sukream.sukream.domains.product.dto.AddProductRequest;
import com.sukream.sukream.domains.product.dto.ProductResponse;
import com.sukream.sukream.domains.product.dto.UpdateProductRequest;
import com.sukream.sukream.domains.product.entity.Product;
import com.sukream.sukream.domains.product.entity.ProductStatus;
import com.sukream.sukream.domains.product.repository.ProductRepository;
import com.sukream.sukream.domains.user.domain.entity.Users;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final UserInfoRepository userInfoRepository;

    // 상품 등록
    @Transactional
    public Long createProduct(AddProductRequest requestDto) {
        Users seller = userInfoRepository.findById(requestDto.getSellerId())
                .orElseThrow(() -> new IllegalArgumentException("해당 판매자를 찾을 수 없습니다."));


        Product product = Product.builder()
                .owner(seller)
                .title(requestDto.getTitle())
                .description(requestDto.getDescription())
                .minPrice(requestDto.getMinPrice())
                .maxPrice(requestDto.getMaxPrice())
                .category(requestDto.getCategory())
                .bidUnit(requestDto.getBidUnit())
                .deadline(requestDto.getDeadline())
                .status(ProductStatus.IN_PROGRESS) // 등록할 땐 진행 중으로
                .bidCount(0)
                .auctionNum(generateAuctionNum())
                .image(requestDto.getImage())
                .chatLink(requestDto.getChatLink())
                .build();

        return productRepository.save(product).getProductId();
    }

    // 상품 조회
    @Transactional(readOnly = true)
    public ProductResponse getProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("상품을 찾을 수 없습니다."));
        return ProductResponse.fromEntity(product);
    }

    // 상품 목록 조회
    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductResponse::fromEntity)
                .collect(Collectors.toList());
    }

    // 상품 수정
    @Transactional
    public void updateProduct(Long id, UpdateProductRequest requestDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("상품을 찾을 수 없습니다."));

        product.updateProduct(requestDto);
    }

    // 상품 삭제
    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException("상품을 찾을 수 없습니다.");
        }
        productRepository.deleteById(id);
    }

    private String generateAuctionNum() {
        return UUID.randomUUID().toString();
    }

}
