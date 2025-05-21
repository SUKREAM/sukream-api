package com.sukream.sukream.domains.bidder.exception;

import com.sukream.sukream.commons.constants.BaseErrorException;
import com.sukream.sukream.commons.constants.ErrorCode;

public class UserNotFoundException extends BaseErrorException {
    public UserNotFoundException() {
        super(ErrorCode.BID_AMOUNT_TOO_LOW);
    }

    public UserNotFoundException(String message) {
        super(ErrorCode.BID_AMOUNT_TOO_LOW, message);
    }
}