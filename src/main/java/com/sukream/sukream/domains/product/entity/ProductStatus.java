package com.sukream.sukream.domains.product.entity;

import lombok.Getter;

@Getter
public enum ProductStatus {
    OPEN("진행 중"),
    CANCELLED("거래 취소"),
    COMPLETED("거래 완료");

    private final String description;

    ProductStatus(String description) {
        this.description = description;
    }

}