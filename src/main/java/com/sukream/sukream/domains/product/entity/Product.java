package com.sukream.sukream.domains.product.entity;

import com.sukream.sukream.commons.jpa.BaseTimeEntity;
import com.sukream.sukream.domains.product.dto.UpdateProductRequest;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.sukream.sukream.domains.user.domain.entity.Users;

import java.time.LocalDateTime;

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
    private Long productId;

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

    @Column(name = "bid_count", nullable = false)
    private int bidCount;

    @Column(name = "bid_unit", nullable = false)
    private int bidUnit;

    @Column(name = "category", nullable = false)
    private String category;

    // 이미지
    @Column(name = "image_url", nullable = false)
    private String image;

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
        this.image = requestDto.getImage();
        this.chatLink = requestDto.getChatLink();
    }

    public LocalDateTime getBidDeadline() {
        return this.deadline;
    }

    // 현재 시간이 상품의 입찰 마감 시각 지났는지 여부 판단
    public boolean isBidDeadlinePassed() {
        return LocalDateTime.now().isAfter(this.deadline);
    }

    // 입찰 들어올 때 입찰 수 1 증가
    public void increaseBidCount() {
        this.bidCount++;
    }

    // 입찰 취소 or 삭제된 경우 입찰 수 감소
    public void decreaseBidCount() {
        if (this.bidCount > 0) {
            this.bidCount--;
        }
    }

}
