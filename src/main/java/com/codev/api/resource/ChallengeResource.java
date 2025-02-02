package com.codev.api.resource;

import com.codev.domain.dto.form.ChallengeDTOForm;
import com.codev.domain.dto.form.SolutionDTOForm;
import com.codev.domain.dto.generics.ItemsWithPagination;
import com.codev.domain.dto.view.ChallengeDTOView;
import com.codev.domain.dto.view.SolutionDTOView;
import com.codev.domain.dto.view.UserDTOView;
import com.codev.domain.enums.ChallengeStatus;
import com.codev.domain.enums.Order;
import com.codev.domain.enums.OrderBy;
import com.codev.domain.exceptions.challenges.CategoryExistsInChallengeException;
import com.codev.domain.exceptions.global.ExceptionResponse;
import com.codev.domain.model.Challenge;
import com.codev.domain.service.ChallengeService;
import com.codev.domain.service.SolutionService;

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
    @RolesAllowed({"ADMIN", "USER"})
    public Response findChallenges(
        @QueryParam("page") Integer page,
        @QueryParam("size") Integer size,
        @QueryParam("status") ChallengeStatus status,
        @QueryParam("category") UUID categoryId,
        @QueryParam("technology") UUID technologyId,
        @QueryParam("order") String order,
        @QueryParam("orderBy") String orderBy,
        @QueryParam("authorId") UUID authorId
    ){
        Order parsedOrder = Order.ASC;
        OrderBy parsedOrderBy = OrderBy.TITLE;

        if (order != null) {
            parsedOrder = Order.valueOf(order.toUpperCase());
        }

        if (orderBy != null) {
            parsedOrderBy = OrderBy.valueOf(orderBy.toUpperCase());
        }
    
        ItemsWithPagination<List<ChallengeDTOView>> challenges =
            challengeService.findChallenges(
                page,
                size,
                status,
                categoryId,
                technologyId,
                parsedOrder,
                parsedOrderBy,
                authorId
            );

        return Response.ok(challenges).build();
    }

    @GET
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/{challengeId}")
    public Response findChallengeById(
        @PathParam("challengeId") UUID challengeId
    ) {
        Challenge challenge = challengeService.findChallengeById(challengeId);
        ChallengeDTOView challengeDTOView = new ChallengeDTOView(challenge);
        return Response.ok(challengeDTOView).build();
    }

    @GET
    @RolesAllowed({"ADMIN", "USER"})
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

    @GET
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/{challengeId}/users")
    public Response findAllUsersForChallenge(
        @PathParam("challengeId") UUID challengeId,
        @QueryParam("page") Integer page,
        @QueryParam("size") Integer size
    ) {
        page = page != null ? page : 0;
        size = size != null ? size : 10;

        List<UserDTOView> users = challengeService.findAllUsersForChallenge(challengeId, page, size);
        return Response.ok(users).build();
    }

    @POST
    @RolesAllowed({"ADMIN"})
    public Response createChallenge(@Valid ChallengeDTOForm challengeDTOForm) {
        ChallengeDTOView challengeDTOView = challengeService.createChallenge(challengeDTOForm);
        return Response.ok(challengeDTOView).status(Response.Status.CREATED).build();
    }

    @POST
    @PermitAll
    @Path("/solutions")
    public Response createSolution(@Valid SolutionDTOForm solutionDTOForm) {
        return Response.ok(solutionService.createSolution(solutionDTOForm)).build();
    }

    @POST
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/{challengeId}/users")
    public Response joinChallenge(
            @PathParam("challengeId") UUID challengeId,
            @HeaderParam("X-User-ID") UUID participantId
    ) {
        try {
            challengeService.joinChallenge(challengeId, participantId);
            return Response.ok().build();

        } catch (SQLException e) {
            ExceptionResponse response = new ExceptionResponse(
                Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                e.getMessage(),
                ""
            );
            return Response.ok(response).status(response.getStatusCode()).build();
        }
    }

    @POST
    @RolesAllowed({"ADMIN"})
    @Path("/{challengeId}/categories/{categoryId}")
    public Response addCategoryInChallenge(
            @PathParam("challengeId") UUID challengeId,
            @PathParam("categoryId") UUID categoryId
    ) {
        try {
            Challenge challenge = challengeService.addCategoryInChallenge(challengeId, categoryId);
            return Response.ok(new ChallengeDTOView(challenge)).build();

        } catch (CategoryExistsInChallengeException | SQLException e) {
            ExceptionResponse response = new ExceptionResponse(
                Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                e.getMessage(),
                ""
            );
            return Response.ok(response).status(response.getStatusCode()).build();
        }
    }

    @PUT
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
            ExceptionResponse response = new ExceptionResponse(
                Response.Status.BAD_REQUEST.getStatusCode(),
                e.getMessage(),
                ""
            );
            return Response.ok(response).status(response.getStatusCode()).build();
        }
    }

    @DELETE
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/{challengeId}/users")
    public Response unjoinChallenge(
            @PathParam("challengeId") UUID challengeId,
            @HeaderParam("X-User-ID") UUID participantId
    ) {
        try {
            challengeService.unjoinChallenge(challengeId, participantId);
            return Response.ok().build();

        } catch (SQLException e) {
            ExceptionResponse response = new ExceptionResponse(
                Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                e.getMessage(),
                ""
            );
            return Response.ok(response).status(response.getStatusCode()).build();
        }
    }

    @DELETE
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
            ExceptionResponse response = new ExceptionResponse(
                Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                e.getMessage(),
                ""
            );
            return Response.ok(response).status(response.getStatusCode()).build();
        }
    }

}