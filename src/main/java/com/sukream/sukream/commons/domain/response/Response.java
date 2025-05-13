package com.sukream.sukream.commons.domain.response;

import com.sukream.sukream.commons.constants.ErrorCode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @Description : Root Response
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Slf4j
public class Response {
    protected boolean success = true;
    protected int errorCode;
    protected String errorMsg;

    private long reqTime;
    private long resTime;
    private String version;

    public static Response success() {
        Response res = new Response();
        res.setSuccess(true);
        res.setErrorCode(ErrorCode.SUCCESS.getValue());
        return res;
    }

    public static Response error(ErrorCode errorCode) {
        Response res = new Response();
        res.setSuccess(false);
        res.setErrorCode(errorCode.getValue());
        res.setErrorMsg(errorCode.getDescription());
        return res;
    }

    public void makeError(ErrorCode errorCode, String errorMessage) {
        this.success = false;
        this.errorCode = errorCode.getValue();
        this.errorMsg = errorMessage;
    }

    public static ResponseEntity<Object> toErrorResponseEntity(ErrorCode e) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Response.error(e));
    }

    public static ResponseEntity<Object> toErrorResponseEntity(int errorCode, String msg) {
        Response response = new Response();
        response.setSuccess(false);
        response.setErrorCode(errorCode);
        response.setErrorMsg(msg);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
