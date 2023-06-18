package com.nmt.FoodOrderAPI.exception;

public class OldPasswordNotMatchException extends RuntimeException {
    public OldPasswordNotMatchException(String message) {
        super(message);
    }
}
