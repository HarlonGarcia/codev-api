package com.codev.domain.exceptions.global;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExceptionResponse {

    private int statusCode;
    private String message;
    private String details;
    private LocalDateTime timestamp;

    public ExceptionResponse(int statusCode, String message, String details) {
        this.statusCode = statusCode;
        this.message = message;
        this.details = details;
        this.timestamp = LocalDateTime.now();
    }
}
