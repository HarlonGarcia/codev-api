package com.codev.domain.exceptions.handler;

import com.codev.domain.exceptions.global.ExceptionResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.time.LocalDateTime;

@Provider
public class EntityNotFoundHandler implements ExceptionMapper<EntityNotFoundException> {

    @Override
    public Response toResponse(EntityNotFoundException exception) {
        ExceptionResponse response = new ExceptionResponse(
            Response.Status.NOT_FOUND.getStatusCode(),
            "Not Found",
            exception.getMessage(),
            LocalDateTime.now()
        );
        return Response.status(response.getStatus()).entity(response).build();
    }
}