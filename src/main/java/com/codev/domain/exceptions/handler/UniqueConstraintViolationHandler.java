package com.codev.domain.exceptions.handler;

import com.codev.domain.exceptions.global.ExceptionResponse;
import com.codev.domain.exceptions.global.UniqueConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class UniqueConstraintViolationHandler implements ExceptionMapper<UniqueConstraintViolationException> {

    @Override
    public Response toResponse(UniqueConstraintViolationException exception) {
        ExceptionResponse exceptionResponse = exception.getExceptionResponse();
        return Response.status(exceptionResponse.getStatusCode()).entity(exceptionResponse).build();
    }

}

