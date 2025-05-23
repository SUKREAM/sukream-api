package com.sukream.sukream.commons.constants;

import lombok.Getter;

/**
 * @Description : Server 모든 Error Code 정의
 */
@Getter
public enum ErrorCode {
    ERR_UNKNOWN(-1, "Unknown(-1)"),
    SUCCESS(200, "Success !!(200)"),
    DELETE_FAIL(501, "Delete Resource Failed(501)"),
    UPDATE_FAIL(502, "Update Resource Failed(502)"),
    RESOURCE_NOT_FOUND(404, "Resource Not Found(404)"),
    FORBIDDEN(403, "접근 권한이 없습니다."),

    // 입찰 관련 에러
    BID_AMOUNT_TOO_LOW(400, "입찰 금액이 현재 최고 입찰가보다 낮습니다."),
    BID_ALREADY_PLACED(409, "이미 입찰한 상품입니다."),
    BID_SELF_OWN_PRODUCT(403, "자신이 등록한 상품에는 입찰할 수 없습니다."),
    BID_INVALID_PRODUCT(404, "존재하지 않는 상품입니다."),
    BID_INVALID_AMOUNT(400, "유효하지 않은 입찰 금액입니다."),
    BID_DEADLINE_EXCEEDED(400, "입찰 마감 시간이 지나 입찰할 수 없습니다."),
    USER_NO_FOUND(404, "사용자가 존재하지 않습니다."),

    // 낙찰 관련 에러
    BIDDER_NOT_FOUND(404, "요청한 입찰자를 찾을 수 없습니다."),
    BIDDER_NOT_BELONG_TO_PRODUCT(400, "해당 입찰자는 이 상품의 입찰자가 아닙니다."),

    // 인증/인가 관련
    AUTHENTICATION_REQUIRED(401, "로그인이 필요합니다."),
    USER_NOT_FOUND(404, "사용자 정보를 찾을 수 없습니다.");

    private final int value;
    private final String description;

    ErrorCode(int statusValue, String description) {
        this.value = statusValue;
        this.description = description;
    }

    public String getErrorMessage() {
        return getDescription();
    }
}

