package com.nmt.FoodOrderAPI.exception;


import org.springframework.http.HttpStatus;

public class BaseException extends RuntimeException {
    private final AbstractError abstractError;

    public BaseException(AbstractError abstractError) {
        super(abstractError.getMessage(), null);
        this.abstractError = abstractError;
    }

    public int getCode() {
        return abstractError.getCode();
    }

    public String getMessage() {
        return abstractError.getMessage();
    }

    public HttpStatus getHttpStatus() {
        return abstractError.getHttpStatus();
    }
}
