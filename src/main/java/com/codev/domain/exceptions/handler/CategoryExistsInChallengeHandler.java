package com.codev.domain.exceptions.handler;

import com.codev.domain.exceptions.challenges.CategoryExistsInChallengeException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class CategoryExistsInChallengeHandler implements ExceptionMapper<CategoryExistsInChallengeException> {
    @Override
    public Response toResponse(CategoryExistsInChallengeException exception) {
        return Response.status(Response.Status.BAD_REQUEST).entity(exception.getExceptionResponse()).build();
    }
}