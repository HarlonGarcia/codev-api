package com.codev.domain.exceptions.handler;

import com.codev.domain.exceptions.challenges.UnjoinNotAcceptedException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class UnjoinNotAcceptedHandler implements ExceptionMapper<UnjoinNotAcceptedException> {
    @Override
    public Response toResponse(UnjoinNotAcceptedException exception) {
        return Response.status(Response.Status.BAD_REQUEST).entity(exception.getExceptionResponse()).build();
    }
}