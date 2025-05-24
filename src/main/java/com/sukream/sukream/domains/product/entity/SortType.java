package com.sukream.sukream.domains.product.entity;

import lombok.Getter;

@Getter
public enum SortType {
    POPULAR("인기순"),
    RECENT("등록순");

    private final String description;

    SortType(String description) {
        this.description = description;
    }
}
