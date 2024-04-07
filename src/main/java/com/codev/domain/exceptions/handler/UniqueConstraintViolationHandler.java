package com.codev.domain.exceptions.handler;

import com.codev.domain.exceptions.global.ErrorResponse;
import com.codev.domain.exceptions.global.UniqueConstraintViolationException;
import jakarta.persistence.PersistenceException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.HashSet;
import java.util.Set;

@Provider
public class UniqueConstraintViolationHandler implements ExceptionMapper<UniqueConstraintViolationException> {

    @Override
    public Response toResponse(UniqueConstraintViolationException exception) {
        ErrorResponse errorResponse = exception.getErrorResponse();
        return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
    }

    private String getConstraintViolationMessage(UniqueConstraintViolationException exception) {
        return exception.getLocalizedMessage();
    }
}

