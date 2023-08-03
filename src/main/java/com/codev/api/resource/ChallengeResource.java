package com.codev.api.resource;

import com.codev.domain.dto.form.ChallengeDTOForm;
import com.codev.domain.dto.form.SolutionDTOForm;
import com.codev.domain.dto.view.ChallengeDTOView;
import com.codev.domain.dto.view.SolutionDTOView;
import com.codev.domain.model.Challenge;
import com.codev.domain.service.ChallengeService;
import com.codev.domain.service.SolutionService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

@Path("challenges")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Challenge")
public class ChallengeResource {

    @Inject
    ChallengeService challengeService;

    @Inject
    SolutionService solutionService;

    @GET
    @Path("/{challengeId}/technologies")
    public Response findAllTechnologiesByChallengeId(@PathParam("challengeId") Long challengeId) {
        return Response.ok(challengeService.findAllTechnologiesByChallengeId(challengeId)).build();
    }

    @GET
    public Response findAllChallengeWithPaging(
            @QueryParam("page") Integer page,
            @QueryParam("size") Integer size
    ){
        page = page != null ? page : 0;
        size = size != null ? size : 10;

        List<ChallengeDTOView> challenges = challengeService.findAllChallengesWithPaging(page, size)
                .stream().map(ChallengeDTOView::new).toList();;

        return Response.ok(challenges).build();
    }

    @GET
    @Path("/{challengeId}")
    public Response findById(@PathParam("challengeId") Long challengeId) {
        Challenge challenge = challengeService.findById(challengeId);
        return Response.ok(new ChallengeDTOView(challenge)).build();
    }

    @GET
    @Path("/{challengeId}/solutions")
    public Response findAllSolutionsByChallengeId(
            @PathParam("challengeId") Long challengeId,
            @HeaderParam("X-User-ID") Long userId,
            @QueryParam("page") Integer page,
            @QueryParam("size") Integer size
    ) {
        page = page != null ? page : 0;
        size = size != null ? size : 10;

        List<SolutionDTOView> solutions = solutionService.findAllSolutionsByChallengeId(challengeId, userId, page, size);
        return Response.ok(solutions).build();
    }

    @POST
    public Response createChallenge(ChallengeDTOForm challengeDTOForm){
        Challenge challenge = challengeService.createChallenge(challengeDTOForm);
        return Response.ok(new ChallengeDTOView(challenge)).status(201).build();
    }

    @POST
    @Path("{challengeId}/solutions")
    public Response createSolution(
            @PathParam("challengeId") Long challengeId,
            SolutionDTOForm solutionDTOForm
    ) {
        return Response.ok(solutionService.createSolution(challengeId, solutionDTOForm)).build();
    }

    @POST
    @Path("{challengeId}/join-challenge")
    public Response joinChallenge(
            @PathParam("challengeId") Long challengeId,
            @HeaderParam("X-User-ID") Long participantId
    ) {
        try {
            challengeService.joinChallenge(challengeId, participantId);
            return Response.ok().build();
        } catch (SQLException e) {
            return Response.ok(e.getStackTrace()).status(400).build();
        }
    }

    @PUT
    @Path("/{challengeId}")
    public Response updateChallenge(
            @PathParam("challengeId") Long challengeId,
            ChallengeDTOForm challengeDTOForm
    ){
        try {
            Challenge challenge = challengeService.updateChallenge(challengeId, challengeDTOForm);
            return Response.ok(new ChallengeDTOView(challenge)).build();
        } catch (InvocationTargetException | IllegalAccessException e) {
            return Response.ok(e.getStackTrace()).status(400).build();
        }
    }

    @DELETE
    @Path("{challengeId}/unjoin-challenge")
    public Response unjoinChallenge(
            @PathParam("challengeId") Long challengeId,
            @HeaderParam("X-User-ID") Long participantId
    ) {
        try {
            challengeService.unjoinChallenge(challengeId, participantId);
            return Response.ok().build();
        } catch (SQLException e) {
            return Response.ok(e.getStackTrace()).status(400).build();
        }
    }

    @DELETE
    @Path("/{challengeId}")
    public Response deactivateChallenge(@PathParam("challengeId") Long challengeId){
        challengeService.deactivateChallenge(challengeId);
        return Response.ok().build();
    }
}