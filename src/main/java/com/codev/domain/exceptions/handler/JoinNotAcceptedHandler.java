package com.codev.domain.exceptions.handler;

import com.codev.domain.exceptions.challenges.JoinNotAcceptedException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class JoinNotAcceptedHandler implements ExceptionMapper<JoinNotAcceptedException> {
    @Override
    public Response toResponse(JoinNotAcceptedException exception) {
        return Response.status(Response.Status.BAD_REQUEST).entity(exception.getExceptionResponse()).build();
    }
}