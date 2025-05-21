package com.sukream.sukream.domains.bidder.exception;

import com.sukream.sukream.commons.constants.BaseErrorException;
import com.sukream.sukream.commons.constants.ErrorCode;

public class BidSelfOwnProductException extends BaseErrorException {
    public BidSelfOwnProductException() {
        super(ErrorCode.BID_SELF_OWN_PRODUCT);
    }
}
