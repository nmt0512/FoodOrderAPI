package com.nmt.FoodOrderAPI.exception;

public class SendingLogoutErrorException extends RuntimeException {
    public SendingLogoutErrorException() {
        super("Sending logout handler error");
    }
}
