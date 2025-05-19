package com.sukream.sukream.domains.bidder.entity;

import com.sukream.sukream.commons.jpa.BaseTimeEntity;
import com.sukream.sukream.domains.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;

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

    @Column(nullable = false)
    private Integer price;

    @Column(name = "is_awarded", nullable = false)
    private Boolean isAwarded;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private String nickname;

    @Builder
    public Bidder(Integer price, Boolean isAwarded, Product product, String nickname) {
        this.price = price;
        this.isAwarded = isAwarded == null ? false : isAwarded;
        this.product = product;
        this.nickname = nickname;
    }

    // 낙찰 여부 변경 등 필요한 비즈니스 메서드만 제공
    public void award() {
        this.isAwarded = true;
    }
}
