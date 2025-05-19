package com.sukream.sukream.domains.entity;

import jakarta.persistence.Entity;

import java.time.LocalDateTime;

@Entity
public class PurchaseHistory {
    private long id;

    private User buyer;
    private Product product;

    private LocalDateTime purchasedTime;
}
