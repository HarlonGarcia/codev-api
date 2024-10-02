package com.codev.domain.exceptions.handler;

import com.codev.domain.exceptions.global.ExceptionResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class IllegalArgumentHandler implements ExceptionMapper<IllegalArgumentException> {

    @Override
    public Response toResponse(IllegalArgumentException exception) {
        ExceptionResponse response;

        if (exception.getMessage() != null) {
             response = new ExceptionResponse(
                Response.Status.BAD_REQUEST.getStatusCode(),
                exception.getMessage(),
                 ""
            );
        } else {
            response = new ExceptionResponse(
                Response.Status.BAD_REQUEST.getStatusCode(),
                "IllegalArgumentException",
                ""
            );
        }
        return Response.status(response.getStatusCode()).entity(response).build();
    }
}