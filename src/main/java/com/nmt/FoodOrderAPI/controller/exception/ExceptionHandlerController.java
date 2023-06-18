package com.nmt.FoodOrderAPI.controller.exception;

import com.nmt.FoodOrderAPI.exception.OldPasswordNotMatchException;
import com.nmt.FoodOrderAPI.response.ResponseData;
import com.nmt.FoodOrderAPI.response.ResponseUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ResponseData<Void>> handleBadCredentialsException(BadCredentialsException badCredentialsException) {
        return ResponseUtils.error(400, "Invalid username or password", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ResponseData<Void>> handleNoSuchElementException(NoSuchElementException noSuchElementException) {
        return ResponseUtils.error(400, "No such element found", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OldPasswordNotMatchException.class)
    public ResponseEntity<ResponseData<Void>> handleOldPasswordNotMatchException(
            OldPasswordNotMatchException oldPasswordNotMatchException
    ) {
        return ResponseUtils.error(400, oldPasswordNotMatchException.getMessage(), HttpStatus.BAD_REQUEST);
    }
}