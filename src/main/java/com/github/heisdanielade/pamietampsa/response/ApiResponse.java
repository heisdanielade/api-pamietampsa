package com.github.heisdanielade.pamietampsa.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ApiResponse<T> {
    private int status;
    private String message;
    private LocalDateTime timestamp;
    private T data;

    public ApiResponse(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

    public ApiResponse(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}
