package com.sukream.sukream.domains.bidder.exception;

import com.sukream.sukream.commons.constants.BaseErrorException;
import com.sukream.sukream.commons.constants.ErrorCode;

public class BidInvalidAmountException extends BaseErrorException {
    public BidInvalidAmountException() {
        super(ErrorCode.BID_INVALID_AMOUNT);
    }
}