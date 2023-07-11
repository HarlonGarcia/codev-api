package com.codev.api.resource;

import com.codev.domain.dto.form.ChallengeDTOForm;
import com.codev.domain.dto.view.ChallengeDTOView;
import com.codev.domain.model.Challenge;
import com.codev.domain.service.ChallengeService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("challenges")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ChallengeResource {

    @Inject
    ChallengeService challengeService;

    @GET
    public Response findAllChallengeWithPaging(
            @QueryParam("page") Integer page,
            @QueryParam("size") Integer size
    ){
        page = page != null ? page : 0;
        size = size != null ? size : 10;

        List<Challenge> challenges = challengeService.findAllChallengeWithPaging(page, size);
        return Response.ok(challenges).build();
    }

    @POST
    public Response createChallenge(ChallengeDTOForm challengeDTOForm){
        ChallengeDTOView challengeDTOView = new ChallengeDTOView(challengeService.createChallenge(challengeDTOForm));
        return Response.ok(challengeDTOView).status(201).build();
    }

}
