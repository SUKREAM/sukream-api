package com.sukream.sukream.domains.product.entity;

import lombok.Getter;

@Getter
public enum ProductStatus {
    OPEN("진행 중"),
    CLOSED("마감 됨"),
    AWARDED("낙찰 완료");

    private final String description;

    ProductStatus(String description) {
        this.description = description;
    }

}