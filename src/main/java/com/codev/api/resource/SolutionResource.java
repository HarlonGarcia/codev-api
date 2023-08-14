package com.codev.api.resource;

import com.codev.domain.exceptions.solutions.LikeNotAcceptedException;
import com.codev.domain.exceptions.solutions.SolutionNotDeletedException;
import com.codev.domain.service.SolutionService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.NoSuchElementException;
import java.util.UUID;

@Path("solutions")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Solution")
public class SolutionResource {

    @Inject
    SolutionService solutionService;

    @RolesAllowed({"USER"})
    @POST
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

    @RolesAllowed({"USER"})
    @DELETE
    @Path("/{solutionId}")
    public Response deleteSolution(
            @PathParam("solutionId") UUID solutionId,
            @HeaderParam("X-User-ID") UUID authorId
    ) {
        try {
            solutionService.deleteSolution(solutionId, authorId);
            return Response.ok().build();
        } catch (NoSuchElementException | SolutionNotDeletedException e) {
            e.printStackTrace();
            return Response.status(404).entity(e.getMessage()).build();
        }
    }

    @RolesAllowed({"USER"})
    @DELETE
    @Path("/{solutionId}/remove-like")
    public Response removeLike(
            @PathParam("solutionId") UUID solutionId,
            @HeaderParam("X-User-ID") UUID userId
    ) {
        try {
            return Response.ok(solutionService.removeLike(solutionId, userId)).build();
        } catch (LikeNotAcceptedException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }

}
