package com.nmt.FoodOrderAPI.exception;

import com.nmt.FoodOrderAPI.response.ResponseData;
import com.nmt.FoodOrderAPI.response.ResponseUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ResponseData<Void>> handleBadCredentialsException(BadCredentialsException badCredentialsException) {
        return ResponseUtils.error(400, "Invalid username or password", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ResponseData<Void>> handleNoSuchElementException(NoSuchElementException noSuchElementException) {
        return ResponseUtils.error(400, noSuchElementException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ResponseData<Void>> handleBaseException(BaseException baseException) {
        return ResponseUtils.error(baseException.getCode(), baseException.getMessage(), baseException.getHttpStatus());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseData<Void>> handleConstraintViolationException(
            ConstraintViolationException constraintViolationException
    ) {
        return ResponseUtils.error(
                400,
                constraintViolationException
                        .getConstraintViolations()
                        .stream()
                        .map(constraintViolation -> constraintViolation
                                .getPropertyPath()
                                .toString() +
                                " " +
                                constraintViolation.getMessage()
                        )
                        .collect(Collectors.joining()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ResponseData<Void>> handleSQLException(SQLException sqlException) {
        if (sqlException.getMessage().contains("duplicate key in object"))
            return ResponseUtils.error(400, "Username already exists", HttpStatus.BAD_REQUEST);
        else {
            sqlException.printStackTrace();
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseData<Void>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException methodArgumentNotValidException
    ) {
        String error = "Some error in request body";
        if (methodArgumentNotValidException.getBindingResult().getFieldError() != null)
            error = methodArgumentNotValidException.getBindingResult().getFieldError().getDefaultMessage();
        return ResponseUtils.error(
                400,
                error,
                HttpStatus.BAD_REQUEST
        );
    }

}
