package com.github.heisdanielade.pamietampsa.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BaseApiResponse<T> {
    private int status;
    private String message;
    private String timestamp;
    private T data;

    public BaseApiResponse(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now().toString();
    }

    public BaseApiResponse(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now().toString();
    }
}
