package com.sukream.sukream.domains.bidder.exception;

import com.sukream.sukream.commons.constants.BaseErrorException;
import com.sukream.sukream.commons.constants.ErrorCode;

public class BidAmountTooLowException extends BaseErrorException {
    public BidAmountTooLowException() {
        super(ErrorCode.BID_AMOUNT_TOO_LOW);
    }
}