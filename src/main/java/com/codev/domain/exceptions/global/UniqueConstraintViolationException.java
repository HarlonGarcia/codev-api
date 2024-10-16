package com.codev.domain.exceptions.global;

import jakarta.ws.rs.core.Response;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Data
public class UniqueConstraintViolationException extends RuntimeException {

    private ExceptionResponse exceptionResponse;

    public UniqueConstraintViolationException(String details) {
        super("Unique constraint violation on a field that must be unique.");

        this.exceptionResponse =
            new ExceptionResponse(
            Response.Status.BAD_REQUEST.getStatusCode(),
            "Unique constraint violation on a field that must be unique.",
            details
        );
    }

}
