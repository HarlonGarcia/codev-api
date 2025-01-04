package com.codev.api.resource;

import com.codev.domain.exceptions.global.ExceptionResponse;
import com.codev.domain.exceptions.solutions.LikeNotAcceptedException;
import com.codev.domain.exceptions.solutions.SolutionNotDeletedException;
import com.codev.domain.service.SolutionService;

import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.UUID;

@Path("solutions")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Solution")
@RequiredArgsConstructor
public class SolutionResource {

    private final SolutionService solutionService;

    @POST
    @PermitAll
    @Path("/{solutionId}/add-like")
    public Response addLike(
            @PathParam("solutionId") UUID solutionId,
            @HeaderParam("X-User-ID") UUID userId
    ) {
        try {
            if (userId == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("User ID not provided in the header.").build();
            }
            return Response.ok(solutionService.addLike(solutionId, userId)).build();

        } catch (LikeNotAcceptedException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }

    @DELETE
    @PermitAll
    @Path("/{solutionId}")
    public Response deleteSolution(
            @PathParam("solutionId") UUID solutionId,
            @HeaderParam("X-User-ID") UUID authorId
    ) {
        try {
            solutionService.deleteSolution(solutionId, authorId);
            return Response.ok().build();

        } catch (SolutionNotDeletedException e) {
            ExceptionResponse response = e.getExceptionResponse();
            return Response.ok(response).status(response.getStatusCode()).build();
        }
    }

    @DELETE
    @PermitAll
    @Path("/{solutionId}/remove-like")
    public Response removeLike(
            @PathParam("solutionId") UUID solutionId,
            @HeaderParam("X-User-ID") UUID userId
    ) {
        try {
            return Response.ok(solutionService.removeLike(solutionId, userId)).build();

        } catch (LikeNotAcceptedException e) {
            ExceptionResponse response = e.getExceptionResponse();
            return Response.ok(response).status(response.getStatusCode()).build();
        }
    }

}
