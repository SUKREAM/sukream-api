package com.sukream.sukream.domains.bidder.exception;

import com.sukream.sukream.commons.constants.BaseErrorException;
import com.sukream.sukream.commons.constants.ErrorCode;

public class BidAlreadyAwardedException extends BaseErrorException {
    public BidAlreadyAwardedException() {
        super(ErrorCode.BID_ALREADY_AWARDED);
    }
}
