package com.sukream.sukream.domains.bidder.entity;

import com.sukream.sukream.commons.jpa.BaseTimeEntity;
import com.sukream.sukream.domains.product.entity.Product;
import com.sukream.sukream.domains.user.domain.entity.Users;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "bidder")
public class Bidder extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bidder_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Column(nullable = false)
    private Integer price;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BidderStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private String nickname;

    @Column(name = "bid_at", nullable = false)
    private LocalDateTime bidAt; // 입찰 시간

    @Column(name = "awarded_at")
    private LocalDateTime awardedAt; // 낙찰 시간

    @Builder
    public Bidder(Users user, Integer price, BidderStatus status, Product product, String nickname, LocalDateTime bidAt) {
        this.user = user;
        this.price = price;
        this.status = status == null ? BidderStatus.PENDING : status;
        this.product = product;
        this.nickname = nickname;
        this.bidAt = bidAt == null ? LocalDateTime.now() : bidAt; // 기본값으로 현재 시각
    }

    @PrePersist
    public void prePersist() {
        if (this.bidAt == null) {
            this.bidAt = LocalDateTime.now();
        }
        if (this.status == null) {
            this.status = BidderStatus.PENDING;
        }
    }

    // 낙찰 처리
    public void award() {
        this.status = BidderStatus.AWARDED;
        this.awardedAt = LocalDateTime.now();
    }

    // 상태 초기화
    public void pending() {
        this.status = BidderStatus.PENDING;
    }
}
