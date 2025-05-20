package com.sukream.sukream.commons.domain.response;

import com.sukream.sukream.commons.constants.SuccessCode;
import com.sukream.sukream.commons.constants.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DataResponse<T> extends Response {
    private T data;

    // 기본 성공 코드
    public DataResponse(T data) {
        super.setSuccess(true);
        super.setErrorCode(ErrorCode.SUCCESS.getValue());
        super.setErrorMsg(ErrorCode.SUCCESS.getDescription());
        this.data = data;
    }

    // 커스텀 성공 코드
    public DataResponse(T data, SuccessCode successCode) {
        super.setSuccess(true);
        super.setErrorCode(successCode.getValue());
        super.setErrorMsg(successCode.getDescription());
        this.data = data;
    }

    public static <T> DataResponse<T> success(T data) {
        return new DataResponse<>(data); // 기본 성공 코드
    }

    public static <T> DataResponse<T> success(T data, SuccessCode successCode) {
        return new DataResponse<>(data, successCode); // 커스텀 성공 코드
    }
}
