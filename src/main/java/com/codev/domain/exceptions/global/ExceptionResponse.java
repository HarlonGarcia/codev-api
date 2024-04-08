package com.codev.domain.exceptions.global;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExceptionResponse {

    private int status;
    private String message;
    private String details;
    private LocalDateTime timestamp;

    public ExceptionResponse(int status, String message, String details, LocalDateTime timestamp) {
        this.status = status;
        this.message = message;
        this.details = details;
        this.timestamp = timestamp;
    }
}
