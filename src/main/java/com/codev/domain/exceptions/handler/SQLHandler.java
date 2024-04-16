package com.codev.domain.exceptions.handler;

import com.codev.domain.exceptions.global.ExceptionResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class SQLHandler implements ExceptionMapper<SQLException> {
    @Override
    public Response toResponse(SQLException exception) {
        ExceptionResponse response = new ExceptionResponse(
            Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
            "Internal Server Error",
            exception.getMessage(),
            LocalDateTime.now()
        );
        return Response.status(response.getStatus()).entity(response).build();
    }
}
