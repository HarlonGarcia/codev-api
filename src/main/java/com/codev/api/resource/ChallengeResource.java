package com.codev.api.resource;

import com.codev.domain.dto.form.ChallengeDTOForm;
import com.codev.domain.dto.form.SolutionDTOForm;
import com.codev.domain.dto.view.ChallengeDTOView;
import com.codev.domain.dto.view.SolutionDTOView;
import com.codev.domain.dto.view.TechnologyDTOView;
import com.codev.domain.enums.OrderBy;
import com.codev.domain.exceptions.challenges.CategoryAlreadyExistsInChallenge;
import com.codev.domain.exceptions.challenges.JoinNotAcceptedException;
import com.codev.domain.exceptions.challenges.UnjoinNotAcceptedException;
import com.codev.domain.model.Challenge;
import com.codev.domain.service.ChallengeService;
import com.codev.domain.service.SolutionService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Path("challenges")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Challenge")
@RequiredArgsConstructor
public class ChallengeResource {

    private final ChallengeService challengeService;

    private final SolutionService solutionService;

    @RolesAllowed({"ADMIN", "USER"})
    @GET
    @Path("/{challengeId}/technologies")
    public Response findAllTechnologiesByChallengeId(@PathParam("challengeId") UUID challengeId) {
        return Response.ok(challengeService.findAllTechnologiesByChallengeId(challengeId)).build();
    }

    @RolesAllowed({"ADMIN", "USER"})
    @GET
    public Response findAllChallengeWithPaging(
            @QueryParam("page") Integer page,
            @QueryParam("size") Integer size,
            @QueryParam("category") UUID categoryId,
            @QueryParam("orderBy") @DefaultValue("ASC") OrderBy orderBy
    ){
        page = page != null ? page : 0;
        size = size != null ? size : 10;

        Set<ChallengeDTOView> challenges = challengeService.findAllChallengesWithPaging    (page, size, categoryId, orderBy);

        return Response.ok(challenges).build();
    }

    @RolesAllowed({"ADMIN", "USER"})
    @GET
    @Path("/{challengeId}")
    public Response findChallengeById(@PathParam("challengeId") UUID challengeId) {
        Challenge challenge = challengeService.findById(challengeId);

        Set<TechnologyDTOView> technologiesDTOView = challenge.getTechnologies().stream()
            .map(TechnologyDTOView::new).collect(Collectors.toSet());

        ChallengeDTOView challengeDTOView = new ChallengeDTOView(challenge, challenge.getCategory(), technologiesDTOView);
        
        return Response.ok(challengeDTOView).build();
    }

    @RolesAllowed({"ADMIN", "USER"})
    @GET
    @Path("/{challengeId}/solutions")
    public Response findAllSolutionsByChallengeId(
            @PathParam("challengeId") UUID challengeId,
            @HeaderParam("X-User-ID") UUID userId,
            @QueryParam("page") Integer page,
            @QueryParam("size") Integer size
    ) {
        page = page != null ? page : 0;
        size = size != null ? size : 10;

        List<SolutionDTOView> solutions = solutionService.findAllSolutionsByChallengeId(challengeId, userId, page, size);
        return Response.ok(solutions).build();
    }

    @RolesAllowed({"ADMIN"})
    @POST
    public Response createChallenge(@Valid ChallengeDTOForm challengeDTOForm) {
        ChallengeDTOView challengeDTOView = challengeService.createChallenge(challengeDTOForm);
        return Response.ok(challengeDTOView).status(Response.Status.CREATED).build();
    }

    @RolesAllowed({"USER"})
    @POST
    @Path("{challengeId}/solutions")
    public Response createSolution(
            @PathParam("challengeId") UUID challengeId,
            @Valid SolutionDTOForm solutionDTOForm
    ) {
        return Response.ok(solutionService.createSolution(challengeId, solutionDTOForm)).build();
    }

    @RolesAllowed({"USER"})
    @POST
    @Path("{challengeId}/join-challenge")
    public Response joinChallenge(
            @PathParam("challengeId") UUID challengeId,
            @HeaderParam("X-User-ID") UUID participantId
    ) {
        try {
            challengeService.joinChallenge(challengeId, participantId);
            return Response.ok().build();
        } catch (JoinNotAcceptedException e) {
            return Response.ok(e.getStackTrace()).status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }

    @RolesAllowed({"ADMIN"})
    @POST
    @Path("/{challengeId}/categories/{categoryId}")
    public Response addCategoryInChallenge(
            @PathParam("challengeId") UUID challengeId,
            @PathParam("categoryId") UUID categoryId
    ) {
        try {
            Challenge challenge = challengeService.addCategoryInChallenge(challengeId, categoryId);
            return Response.ok(new ChallengeDTOView(challenge)).build();

        } catch (CategoryAlreadyExistsInChallenge | SQLException e) {
            return Response.ok(e.getStackTrace()).status(Response.Status.BAD_REQUEST).build();
        }
    }

    @RolesAllowed({"ADMIN"})
    @PUT
    @Path("/{challengeId}")
    public Response updateChallenge(
            @PathParam("challengeId") UUID challengeId,
            @Valid ChallengeDTOForm challengeDTOForm
    ){
        try {
            Challenge challenge = challengeService.updateChallenge(challengeId, challengeDTOForm);
            return Response.ok(new ChallengeDTOView(challenge)).build();
        } catch (InvocationTargetException | IllegalAccessException e) {
            return Response.ok(e.getStackTrace()).status(Response.Status.BAD_REQUEST).build();
        }
    }

    @RolesAllowed({"USER"})
    @DELETE
    @Path("{challengeId}/unjoin-challenge")
    public Response unjoinChallenge(
            @PathParam("challengeId") UUID challengeId,
            @HeaderParam("X-User-ID") UUID participantId
    ) {
        try {
            challengeService.unjoinChallenge(challengeId, participantId);
            return Response.ok().build();
        } catch (UnjoinNotAcceptedException e) {
            return Response.ok(e.getStackTrace()).status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }

    @RolesAllowed({"ADMIN"})
    @DELETE
    @Path("/{challengeId}")
    public Response deactivateChallenge(@PathParam("challengeId") UUID challengeId){
        challengeService.deactivateChallenge(challengeId);
        return Response.ok().build();
    }

    @RolesAllowed({"ADMIN"})
    @DELETE
    @Path("/{challengeId}/categories")
    public Response removeCategoryInChallenge(@PathParam("challengeId") UUID challengeId){
        try {
            challengeService.removeCategoryInChallenge(challengeId);
            return Response.ok().build();
        } catch (SQLException e) {
            return Response.ok(e.getStackTrace()).status(Response.Status.BAD_REQUEST).build();
        }
    }

}