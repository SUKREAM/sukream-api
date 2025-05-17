package com.sukream.sukream.domains.bidder.entity;

import com.sukream.sukream.commons.jpa.BaseTimeEntity;
import com.sukream.sukream.domains.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
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
    private Boolean isAwarded = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private String nickname;

}
