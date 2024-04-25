package com.codev.domain.exceptions.handler;

import com.codev.domain.exceptions.global.ExceptionResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class EntityNotFoundHandler implements ExceptionMapper<EntityNotFoundException> {

    @Override
    public Response toResponse(EntityNotFoundException exception) {
        ExceptionResponse response = new ExceptionResponse(
            Response.Status.NOT_FOUND.getStatusCode(),
            exception.getMessage(),
            ""
        );
        return Response.status(response.getStatusCode()).entity(response).build();
    }
}