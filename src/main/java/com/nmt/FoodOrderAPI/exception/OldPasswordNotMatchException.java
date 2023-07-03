package com.nmt.FoodOrderAPI.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class OldPasswordNotMatchException extends RuntimeException {
    public OldPasswordNotMatchException(String message) {
        super(message);
    }

}
