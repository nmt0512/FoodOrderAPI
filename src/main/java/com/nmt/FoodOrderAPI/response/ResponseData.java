package com.nmt.FoodOrderAPI.response;

import com.nmt.FoodOrderAPI.enums.ResponseStatusCode;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;


@Data
public class ResponseData<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String time;

    private int code;

    private String message;

    private T data;

    public ResponseData() {
        this.code = 0;
        this.time =
                LocalDateTime.now()
                        .atZone(ZoneId.systemDefault()).toLocalDateTime().toString();
        this.message = "Thành công!";
    }

    ResponseData<T> success(T data) {
        this.code = 200;
        this.data = data;
        return this;
    }

    ResponseData<T> success(T data, int code, String message) {
        this.data = data;
        this.code = code;
        this.message = message;
        return this;
    }

    ResponseData<T> error(int code, String message) {
        this.code = code;
        this.message = message;
        return this;
    }

    ResponseData<T> error(int code, String message, T data) {
        this.data = data;
        this.code = code;
        this.message = message;
        return this;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ResponseData(ResponseStatusCode responseStatusCode) {
        this.code = responseStatusCode.getValue();
        this.time =
                LocalDateTime.now()
                        .atZone(ZoneId.systemDefault()).toLocalDateTime().toString();
        this.message = responseStatusCode.getDescription();
    }
}
