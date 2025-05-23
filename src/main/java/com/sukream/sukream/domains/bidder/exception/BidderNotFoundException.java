package com.sukream.sukream.domains.bidder.exception;

import com.sukream.sukream.commons.constants.BaseErrorException;
import com.sukream.sukream.commons.constants.ErrorCode;

public class BidderNotFoundException extends BaseErrorException {
    public BidderNotFoundException() {
        super(ErrorCode.BIDDER_NOT_FOUND);
    }
}
