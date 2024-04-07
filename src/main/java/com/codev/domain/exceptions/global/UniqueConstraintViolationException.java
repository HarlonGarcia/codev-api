package com.codev.domain.exceptions.global;

import lombok.Data;

@Data
public class UniqueConstraintViolationException extends RuntimeException {

    private final ErrorResponse errorResponse;

    public UniqueConstraintViolationException(ErrorResponse errorResponse) {
        super(errorResponse.getViolations().toString());
        this.errorResponse = errorResponse;
    }

}

