package com.codev.domain.exceptions.global;

import jakarta.ws.rs.core.Response;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class UniqueConstraintViolationException extends RuntimeException {

    private final ErrorResponse errorResponse;

    public UniqueConstraintViolationException(String fieldName) {
        super("Unique constraint violation on a field that must be unique.");

        Violation violation = new Violation(fieldName, "Unique constraint violation on a field that must be unique.");
        this.errorResponse = new ErrorResponse(Response.Status.BAD_REQUEST.getStatusCode(), "unique_constraint_violation", violation);
    }

}
