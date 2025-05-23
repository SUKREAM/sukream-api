package com.sukream.sukream.domains.bidder.exception;

import com.sukream.sukream.commons.constants.BaseErrorException;
import com.sukream.sukream.commons.constants.ErrorCode;

public class UnauthorizedAwardAccessException extends BaseErrorException {
    public UnauthorizedAwardAccessException() {
        super(ErrorCode.FORBIDDEN);
    }
}
