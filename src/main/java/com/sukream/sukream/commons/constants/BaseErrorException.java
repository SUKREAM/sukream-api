package com.sukream.sukream.commons.constants;

import lombok.Getter;

@Getter
public class BaseErrorException extends RuntimeException {
    private final int status;
    private final String errorMessage;

    public BaseErrorException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.status = errorCode.getValue();
        this.errorMessage = errorCode.getDescription();
    }

    public BaseErrorException(ErrorCode errorCode, String customMessage) {
        super(customMessage);
        this.status = errorCode.getValue();
        this.errorMessage = customMessage;
    }
}