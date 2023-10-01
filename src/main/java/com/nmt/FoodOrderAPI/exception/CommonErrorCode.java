package com.nmt.FoodOrderAPI.exception;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum CommonErrorCode implements AbstractError {
    OLD_PASSWORD_NOT_MATCH(
            400,
            "Old password request doesn't match with current user password",
            HttpStatus.BAD_REQUEST),
    RECEIVE_PENDING_PREPAID_BILL_FAILED(
            422,
            "Bill was received by a shipper and update failed",
            HttpStatus.UNPROCESSABLE_ENTITY
    );

    private final int code;

    private final String message;

    private final HttpStatus httpStatus;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
