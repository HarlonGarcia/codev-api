package com.codev.api.resource;

import com.codev.domain.exceptions.solutions.LikeNotAcceptedException;
import com.codev.domain.exceptions.solutions.SolutionNotDeletedException;
import com.codev.domain.service.SolutionService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestResponse;

@Path("solutions")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Solution")
public class SolutionResource {

    @Inject
    SolutionService solutionService;

    @POST
    @Path("/{solutionId}/add-like")
    public Response addLike(
            @PathParam("solutionId") Long solutionId,
            @HeaderParam("X-User-ID") Long userId
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
    @Path("/{solutionId}/remove-like")
    public Response removeLike(
            @PathParam("solutionId") Long solutionId,
            @HeaderParam("X-User-ID") Long userId
    ) {
        try {
            return Response.ok(solutionService.removeLike(solutionId, userId)).build();
        } catch (LikeNotAcceptedException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }

    @DELETE
    @Path("/{solutionId}")
    public Response removeSolution(
            @PathParam("solutionId") Long solutionId,
            @HeaderParam("X-User-ID") Long authorId
    ) {
        try {
            solutionService.deleteSolution(solutionId, authorId);
            return Response.ok().build();
        } catch (SolutionNotDeletedException e) {
            return Response.ok(e.getMessage()).status(RestResponse.Status.BAD_REQUEST).build();
        }
    }

}
