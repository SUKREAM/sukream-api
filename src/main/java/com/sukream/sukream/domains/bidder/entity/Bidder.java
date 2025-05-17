package com.sukream.sukream.domains.bidder.entity;

import com.sukream.sukream.domains.product.entity.Product;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Bidder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Integer price;
    private LocalDateTime submittedOn;
    private Boolean isAwarded = false; // true면 낙찰

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
}
