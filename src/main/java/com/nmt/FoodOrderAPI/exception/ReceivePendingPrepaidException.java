package com.nmt.FoodOrderAPI.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class ReceivePendingPrepaidException extends RuntimeException {
    public ReceivePendingPrepaidException(String message) {
        super(message);
    }
}
