package com.sukream.sukream.domains.product.service;

import com.sukream.sukream.domains.auth.repository.UserInfoRepository;
import com.sukream.sukream.domains.bidder.repository.BidderRepository;
import com.sukream.sukream.domains.product.repository.ProductRepository;
import com.sukream.sukream.domains.product.dto.AddProductRequest;
import com.sukream.sukream.domains.product.dto.ProductResponse;
import com.sukream.sukream.domains.product.dto.UpdateProductRequest;
import com.sukream.sukream.domains.product.entity.Product;
import com.sukream.sukream.domains.product.entity.ProductStatus;
import com.sukream.sukream.domains.user.domain.entity.Users;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final UserInfoRepository userInfoRepository;
    private final BidderRepository bidderRepository;

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
                .status(ProductStatus.OPEN)
                .auctionNum(generateAuctionNum())
                .image(requestDto.getImage() != null ? Base64.getDecoder().decode(requestDto.getImage()) : null)
                .chatLink(requestDto.getChatLink())
                .build();

        return productRepository.save(product).getId();
    }

    // 상품 상세 조회
    @Transactional
    public ProductResponse getProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("상품을 찾을 수 없습니다."));

        // 마감 여부 확인 및 상태 변경
        if (product.isBidDeadlinePassed() && product.getStatus() == ProductStatus.OPEN) {
            product.closeAuction();
        }
        // 입찰 수 실시간 조회
        int bidCount = bidderRepository.countByProduct_Id(id);

        return ProductResponse.fromEntityAndBidCount(product, bidCount);
    }

    // 카테고리 별 상품 목록 조회 및 정렬
    @Transactional
    public List<ProductResponse> getAllProducts(String category, String sort) {
        List<Product> productsForDeadlineCheck = productRepository.findAllForDeadlineCheck(category);

        for (Product product : productsForDeadlineCheck) {
            if (product.isBidDeadlinePassed() && product.getStatus() == ProductStatus.OPEN) {
                product.closeAuction();
            }
        }
        return productRepository.findByCategoryAndSort(category, sort);
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

    // 소유자 검증 메서드
    @Transactional(readOnly = true)
    public void validateProductOwner(Long productId, Long userId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("상품을 찾을 수 없습니다."));

        if (!product.getOwner().getId().equals(userId)) {
            throw new IllegalArgumentException("해당 상품에 대한 권한이 없습니다.");
        }
    }

    private String generateAuctionNum() {
        return UUID.randomUUID().toString();
    }
}
