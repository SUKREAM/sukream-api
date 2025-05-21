package com.sukream.sukream.domains.bidder.exception;


import com.sukream.sukream.commons.constants.BaseErrorException;
import com.sukream.sukream.commons.constants.ErrorCode;

public class BidDeadlineExceededException extends BaseErrorException {
    public BidDeadlineExceededException() {
        super(ErrorCode.BID_DEADLINE_EXCEEDED);
    }
}