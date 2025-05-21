package com.sukream.sukream.domains.product.entity;

public enum ProductStatus {
    IN_PROGRESS("진행 중"),
    CLOSED("마감됨");

    private final String description;

    ProductStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
