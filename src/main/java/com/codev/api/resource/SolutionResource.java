package com.codev.api.resource;

import com.codev.domain.dto.form.SolutionDTOForm;
import com.codev.domain.service.SolutionService;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@Path("solutions")
public class SolutionResource {

    @Inject
    SolutionService solutionService;

    @POST
    @Path("{solutionId}/users/{userId}")
    public Response likeOrDislikeInSolution(
            @PathParam("solutionId") Long solutionId,
            @PathParam("userId") Long userId
    ) {
        return Response.ok(solutionService.likeOrDislikeInSolution(solutionId, userId)).build();
    }

}
