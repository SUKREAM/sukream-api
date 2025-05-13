package com.sukream.sukream.commons.constants;

import lombok.Getter;

/**
 * @Description : Server 모든 Error Code 정의
 */
@Getter
public enum ErrorCode {
    ERR_UNKNOWN(-1, "Unknown(-1)"),
    SUCCESS(200, "Success !!(200)"),
    DELETE_FAIL(501,"Delete Resource Failed(501)"),
    UPDATE_FAIL(502,"Update Resource Failed(502)"),
    RESOURCE_NOT_FOUND(404,"Resource Not Found(404)"),
    ;
    private final int value;
    private final String description;

    ErrorCode(int errorValue, String description) {
        this.value = errorValue;
        this.description = description;
    }

    public String getErrorMessage() {
        return getDescription();
    }
}

