package com.sukream.sukream.domains.bidder.exception;

import com.sukream.sukream.commons.constants.BaseErrorException;
import com.sukream.sukream.commons.constants.ErrorCode;

public class BidderNotBelongToProductException extends BaseErrorException {
    public BidderNotBelongToProductException() {
        super(ErrorCode.BIDDER_NOT_BELONG_TO_PRODUCT);
    }
}
