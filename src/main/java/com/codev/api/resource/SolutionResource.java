package com.codev.api.resource;

import com.codev.domain.service.ChallengeService;
import com.codev.domain.service.SolutionService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SolutionResource {

    @Inject
    SolutionService solutionService;

    @GET
    @Path("/challenges/{challengeId}/solutions")
    public Response findAllSolutionsByChallengeId(@PathParam("challengeId") Long challengeId) {
        return Response.ok().build();
    }

}
