package com.sukream.sukream.commons.constants;

import lombok.Getter;

@Getter
public enum SuccessCode {
    PRODUCT_CREATE_SUCCESS(201, "상품이 성공적으로 등록되었습니다!"),
    PRODUCT_UPDATE_SUCCESS(200, "상품이 성공적으로 수정되었습니다!"),
    PRODUCT_DELETE_SUCCESS(204, "상품이 성공적으로 삭제되었습니다."),
    PRODUCT_READ_SUCCESS(200, "상품이 성공적으로 조회되었습니다."),
    ;
    private final int value;
    private final String description;

    SuccessCode(int statusValue, String description) {
        this.value = statusValue;
        this.description = description;
    }

    public String getSuccessMessage() {
        return getDescription();
    }
}
