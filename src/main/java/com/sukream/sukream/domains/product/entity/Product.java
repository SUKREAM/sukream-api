package com.sukream.sukream.domains.product.entity;

import com.sukream.sukream.commons.jpa.BaseTimeEntity;
import com.sukream.sukream.domains.product.dto.UpdateProductRequest;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.sukream.sukream.domains.user.domain.entity.Users;

import java.time.LocalDateTime;
import java.util.Base64;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자
@AllArgsConstructor // 모든 필드 생성자
@Builder
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", updatable = false)
    private Users owner;

    @Column(name = "min_price", nullable = false)
    private int minPrice;

    @Column(name = "max_price", nullable = false)
    private int maxPrice;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "title", nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ProductStatus status;

    @Column(name = "deadline", nullable = false)
    private LocalDateTime deadline;

    @Column(name = "auction_num", nullable = false)
    private String auctionNum;

    @Column(name = "bid_unit", nullable = false)
    private int bidUnit;

    @Column(name = "category", nullable = false)
    private String category;

    // 이미지
    @Lob
    @Column(name = "image_blob", nullable = true)
    private byte[] image;

    // 오픈채팅방 링크
    @Column(name = "chat_link")
    private String chatLink;

    public void updateProduct(UpdateProductRequest requestDto) {
        this.title = requestDto.getTitle();
        this.description = requestDto.getDescription();
        this.minPrice = requestDto.getMinPrice();
        this.maxPrice = requestDto.getMaxPrice();
        this.category = requestDto.getCategory();
        this.bidUnit = requestDto.getBidUnit();
        this.deadline = requestDto.getDeadline();
        this.image = requestDto.getImage() != null ? Base64.getDecoder().decode(requestDto.getImage()) : null;
        this.chatLink = requestDto.getChatLink();
        this.status = ProductStatus.valueOf(requestDto.getStatus().toUpperCase());
    }

    public LocalDateTime getBidDeadline() {
        return this.deadline;
    }

    // 현재 시간이 상품의 입찰 마감 시각 지났는지 여부 판단
    public boolean isBidDeadlinePassed() {
        return LocalDateTime.now().isAfter(this.deadline);
    }

    // 입찰 가능 여부
    public boolean isBiddable() {
        return this.status == ProductStatus.OPEN && !isBidDeadlinePassed();
    }

    // 경매 마감 상태
    public void closeAuction() {
        this.status = ProductStatus.CLOSED;
    }

    // 낙찰 완료 상태
    public void awardAuction() {
        this.status = ProductStatus.AWARDED;
    }
}
