package com.sukream.sukream.domains.bidder.exception;

import com.sukream.sukream.commons.constants.BaseErrorException;
import com.sukream.sukream.commons.constants.ErrorCode;

public class BidAlreadyPlacedException extends BaseErrorException {
    public BidAlreadyPlacedException() {
        super(ErrorCode.BID_ALREADY_PLACED);
    }
}
