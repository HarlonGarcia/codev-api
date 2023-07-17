package com.codev.api.resource;

import com.codev.domain.dto.form.SolutionDTOForm;
import com.codev.domain.dto.view.SolutionDTOView;
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
    @Path("challenges/{challengeId}")
    public Response createSolution(
            @PathParam("challengeId") Long challengeId,
            SolutionDTOForm solutionDTOForm
    ) {
        return Response.ok(solutionService.createSolution(challengeId, solutionDTOForm)).build();
    }

}
