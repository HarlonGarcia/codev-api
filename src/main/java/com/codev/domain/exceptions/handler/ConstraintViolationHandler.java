package com.codev.domain.exceptions.handler;

import com.codev.domain.exceptions.global.ErrorResponse;
import com.codev.domain.exceptions.global.Violation;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.ArrayList;
import java.util.List;

@Provider
public class ConstraintViolationHandler implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        List<Violation> violations = new ArrayList<>();
        for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
            violations.add(new Violation(violation.getPropertyPath().toString(),
                violation.getMessage()));
        }

        ErrorResponse errorResponse = new ErrorResponse(Response.Status.BAD_REQUEST.getStatusCode(),
            "Constraint Violation", violations);
        return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
    }
}

