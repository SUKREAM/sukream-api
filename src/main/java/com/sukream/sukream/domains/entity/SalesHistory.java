package com.sukream.sukream.domains.entity;

import jakarta.persistence.Entity;

import java.time.LocalDateTime;

@Entity
public class SalesHistory {
    private long id;
    private User seller;
    private Product product;
    private LocalDateTime createTime;
}
