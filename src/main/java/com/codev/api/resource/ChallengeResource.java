package com.codev.api.resource;

import com.codev.domain.dto.form.ChallengeDTOForm;
import com.codev.domain.dto.form.SolutionDTOForm;
import com.codev.domain.dto.view.ChallengeDTOView;
import com.codev.domain.dto.view.SolutionDTOView;
import com.codev.domain.enums.OrderBy;
import com.codev.domain.exceptions.challenges.CategoryAlreadyExistsInChallenge;
import com.codev.domain.exceptions.challenges.JoinNotAcceptedException;
import com.codev.domain.exceptions.challenges.UnjoinNotAcceptedException;
import com.codev.domain.model.Challenge;
import com.codev.domain.service.ChallengeService;
import com.codev.domain.service.SolutionService;
import io.quarkus.cache.CacheInvalidateAll;
import io.quarkus.cache.CacheKey;
import io.quarkus.cache.CacheResult;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
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

    @GET
    @CacheResult(cacheName = "challenge-cache")
    @PermitAll
    public Response findAllChallengeWithPagingByCategoryId(
            @QueryParam("page") Integer page,
            @QueryParam("size") Integer size,
            @CacheKey @QueryParam("category") UUID categoryId,
            @QueryParam("orderBy") @DefaultValue("ASC") OrderBy orderBy
    ){
        page = page != null ? page : 0;
        size = size != null ? size : 10;

        Set<ChallengeDTOView> challenges = challengeService.findAllChallengeWithPagingByCategoryId(page, size, categoryId, orderBy);

        return Response.ok(challenges).build();
    }

    @GET
    @CacheResult(cacheName = "challenge-cache")
    @PermitAll
    @Path("/{challengeId}")
    public Response findChallengeById(
        @CacheKey @PathParam("challengeId") UUID challengeId
    ) {
        Challenge challenge = challengeService.findChallengeById(challengeId);
        ChallengeDTOView challengeDTOView = new ChallengeDTOView(challenge);
        return Response.ok(challengeDTOView).build();
    }

    @GET
    @CacheResult(cacheName = "solution-cache")
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/{challengeId}/solutions")
    public Response findAllSolutionsByChallengeId(
            @CacheKey @PathParam("challengeId") UUID challengeId,
            @HeaderParam("X-User-ID") UUID userId,
            @QueryParam("page") Integer page,
            @QueryParam("size") Integer size
    ) {
        page = page != null ? page : 0;
        size = size != null ? size : 10;

        List<SolutionDTOView> solutions = solutionService.findAllSolutionsByChallengeId(challengeId, userId, page, size);
        return Response.ok(solutions).build();
    }

    @POST
    @CacheInvalidateAll(cacheName = "challenge-cache")
    @RolesAllowed({"ADMIN"})
    public Response createChallenge(@Valid ChallengeDTOForm challengeDTOForm) {
        ChallengeDTOView challengeDTOView = challengeService.createChallenge(challengeDTOForm);
        return Response.ok(challengeDTOView).status(Response.Status.CREATED).build();
    }

    @POST
    @CacheInvalidateAll(cacheName = "solution-cache")
    @RolesAllowed({"USER"})
    @Path("/solutions")
    public Response createSolution(@Valid SolutionDTOForm solutionDTOForm) {
        return Response.ok(solutionService.createSolution(solutionDTOForm)).build();
    }

    @POST
    @RolesAllowed({"USER"})
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

    @POST
    @RolesAllowed({"ADMIN"})
    @CacheInvalidateAll(cacheName = "challenge-cache")
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

    @PUT
    @CacheInvalidateAll(cacheName = "challenge-cache")
    @RolesAllowed({"ADMIN"})
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

    @DELETE
    @RolesAllowed({"USER"})
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

    @DELETE
    @CacheInvalidateAll(cacheName = "challenge-cache")
    @RolesAllowed({"ADMIN"})
    @Path("/{challengeId}")
    public Response deactivateChallenge(@PathParam("challengeId") UUID challengeId){
        challengeService.deactivateChallenge(challengeId);
        return Response.ok().build();
    }

    @DELETE
    @RolesAllowed({"ADMIN"})
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