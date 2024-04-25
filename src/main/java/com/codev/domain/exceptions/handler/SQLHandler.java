package com.codev.domain.exceptions.handler;

import com.codev.domain.exceptions.global.ExceptionResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

import java.sql.SQLException;

public class SQLHandler implements ExceptionMapper<SQLException> {
    @Override
    public Response toResponse(SQLException exception) {
        ExceptionResponse response = new ExceptionResponse(
            Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
            "Internal Server Error",
            exception.getMessage()
        );
        return Response.status(response.getStatusCode()).entity(response).build();
    }
}
