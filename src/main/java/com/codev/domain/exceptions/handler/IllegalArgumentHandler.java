package com.codev.domain.exceptions.handler;

import com.codev.domain.exceptions.global.ExceptionResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.time.LocalDateTime;

@Provider
public class IllegalArgumentHandler implements ExceptionMapper<IllegalArgumentException> {

    @Override
    public Response toResponse(IllegalArgumentException exception) {
        ExceptionResponse response = null;

        if (exception.getMessage() != null) {
             response = new ExceptionResponse(
                Response.Status.BAD_REQUEST.getStatusCode(),
                "Bad Request",
                exception.getMessage(),
                LocalDateTime.now()
            );
        } else {
            response = new ExceptionResponse(
                Response.Status.BAD_REQUEST.getStatusCode(),
                "Bad Request",
                "IllegalArgumentException",
                LocalDateTime.now()
            );
        }
        return Response.status(response.getStatus()).entity(response).build();
    }
}