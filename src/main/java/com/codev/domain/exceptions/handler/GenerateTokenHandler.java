package com.codev.domain.exceptions.handler;

import com.codev.domain.exceptions.global.ExceptionResponse;
import com.codev.domain.exceptions.token.GenerateTokenException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class GenerateTokenHandler implements ExceptionMapper<GenerateTokenException> {

    @Override
    public Response toResponse(GenerateTokenException exception) {
        ExceptionResponse response = exception.getExceptionResponse();
        return Response.status(response.getStatusCode()).entity(response).build();
    }
}
