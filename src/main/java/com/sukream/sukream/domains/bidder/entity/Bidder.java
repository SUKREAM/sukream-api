package com.sukream.sukream.domains.bidder.entity;

import com.sukream.sukream.commons.jpa.BaseTimeEntity;
import com.sukream.sukream.domains.product.entity.Product;
import com.sukream.sukream.domains.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.*;
import com.sukream.sukream.domains.user.domain.entity.User;

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
    private User user;

    @Column(nullable = false)
    private Integer price;

    @Column(name = "is_awarded", nullable = false)
    private Boolean isAwarded;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BidderStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private String nickname;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @Builder
    public Bidder(User user, Integer price, Boolean isAwarded, BidderStatus status, Product product, String nickname) {
        this.user = user;
        this.price = price;
        this.isAwarded = isAwarded == null ? false : isAwarded;
        this.status = status == null ? BidderStatus.PENDING : status;
        this.product = product;
        this.nickname = nickname;
        this.user = user;
    }

    // 낙찰 처리
    public void award() {
        this.isAwarded = true;
        this.status = BidderStatus.AWARDED;
    }

    // 상태 초기화
    public void pending() {
        this.isAwarded = false;
        this.status = BidderStatus.PENDING;
    }}
