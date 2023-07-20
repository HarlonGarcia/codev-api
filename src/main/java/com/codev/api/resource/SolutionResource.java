package com.codev.api.resource;

import com.codev.domain.exceptions.solutions.LikeNotAcceptedException;
import com.codev.domain.service.SolutionService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("solutions")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Solution")
public class SolutionResource {

    @Inject
    SolutionService solutionService;

    @POST
    @Path("{solutionId}/users/{userId}")
    public Response addLike(
            @PathParam("solutionId") Long solutionId,
            @PathParam("userId") Long userId
    ) {
        try {
            return Response.ok(solutionService.addLike(solutionId, userId)).build();
        } catch (LikeNotAcceptedException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }

    @DELETE
    @Path("{solutionId}/users/{userId}")
    public Response removeLike(
            @PathParam("solutionId") Long solutionId,
            @PathParam("userId") Long userId
    ) {
        try {
            return Response.ok(solutionService.removeLike(solutionId, userId)).build();
        } catch (LikeNotAcceptedException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }
}
