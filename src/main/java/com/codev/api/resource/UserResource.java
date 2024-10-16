package com.codev.api.resource;

import com.codev.api.security.auth.AuthRequest;
import com.codev.api.security.auth.AuthResponse;
import com.codev.domain.dto.form.UserDTOForm;
import com.codev.domain.dto.form.UserFiltersDTOForm;
import com.codev.domain.dto.form.UserUpdateDTOForm;
import com.codev.domain.dto.view.ChallengeDTOView;
import com.codev.domain.dto.view.UserDTOView;
import com.codev.domain.exceptions.global.ExceptionResponse;
import com.codev.domain.exceptions.token.GenerateTokenException;
import com.codev.domain.exceptions.users.*;
import com.codev.domain.service.ChallengeService;
import com.codev.domain.service.UserService;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.UUID;

@Path("users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "User")
@RequiredArgsConstructor
public class UserResource {

    private final UserService userService;

    private final ChallengeService challengeService;

    @GET
    @RolesAllowed({"USER"})
    public Response findAllUsers(
        @QueryParam("startsWith") @DefaultValue("") String startsWith,
        @QueryParam("page") Integer page,
        @QueryParam("size") Integer size
    ) {
        page = page != null ? page : 0;
        size = size != null ? size : 10;

        UserFiltersDTOForm filters = new UserFiltersDTOForm(startsWith);
        List<UserDTOView> users = userService.findAllUsers(filters, page, size);
        return Response.ok(users).build();
    }

    @RolesAllowed({"USER"})
    @GET
    @Path("followed")
    public Response findAllFollowedUsers(
        @HeaderParam("X-User-ID") UUID followerId,
        @QueryParam("page") Integer page,
        @QueryParam("size") Integer size
    ) {
        page = page != null ? page : 0;
        size = size != null ? size : 10;

        List<UserDTOView> users = userService.findAllFollowedUsers(followerId, page, size);
        return Response.ok(users).build();
    }

    @GET
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/{userId}/challenges")
    public Response findUserChallenges(
        @PathParam("userId") UUID userId,
        @QueryParam("page") Integer page,
        @QueryParam("size") Integer size
    ) {
        page = page != null ? page : 0;
        size = size != null ? size : 10;

        List<ChallengeDTOView> challenges = challengeService.findUserChallenges(userId, page, size);
        return Response.ok(challenges).build();
    }

    @POST
    @RolesAllowed({"USER"})
    @Path("followed/{followedId}")
    public Response followUser(
        @PathParam("followedId") UUID followedId,
        @HeaderParam("X-User-ID") UUID followerId
    ) {
        userService.followUser(followedId, followerId);
        return Response.ok(new UserIsAlreadyBeingFollowedResponse()).status(Response.Status.BAD_REQUEST).build();
    }

    @POST
    @PermitAll
    @Path("/signup")
    public Response createUser(@Valid UserDTOForm userDTOForm) throws GenerateTokenException {
        AuthResponse authResponse = userService.createUser(userDTOForm);
        return Response.ok(authResponse).status(Response.Status.CREATED).build();
    }

    @RolesAllowed({"ADMIN"})
    @POST
    @Path("/{userId}/add-admin-role")
    public Response addAdminRoleInUser(@PathParam("userId") UUID userId) {
        try {
            UserDTOView userDTOView = userService.addAdminRoleInUser(userId);
            return Response.ok(userDTOView).build();

        } catch (UserDeactivatedException e) {
            ExceptionResponse response = e.getExceptionResponse();
            return Response.ok(response).status(response.getStatusCode()).build();

        } catch (UserHasAdminRoleException e) {
            ExceptionResponse response = e.getExceptionResponse();
            return Response.ok(response).status(response.getStatusCode()).build();
        }
    }

    @PermitAll
    @POST
    @Path("/login")
    public Response login(AuthRequest authRequest) {
        try {
            return Response.ok(userService.login(authRequest)).build();

        } catch (InvalidLoginException e) {
            ExceptionResponse response = e.getExceptionResponse();
            return Response.ok(response).status(response.getStatusCode()).build();
        }
    }

    @PUT
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/{userId}")
    public Response updateUser(
        @PathParam("userId") UUID userId,
        @Valid UserUpdateDTOForm userDTOForm
    ) {
        try {
            UserDTOView userDTOView = userService.updateUser(userId, userDTOForm);

            return Response.ok(userDTOView).build();
        } catch (Exception e) {
            ExceptionResponse response = new ExceptionResponse(
                Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                e.getMessage(),
                ""
            );

            return Response.ok(response).status(response.getStatusCode()).build();
        }
    }

    @DELETE
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/{userId}")
    public Response deactivateUser(@PathParam("userId") UUID userId) {
        try {
            userService.deactivateUser(userId);
            return Response.status(Response.Status.OK.getStatusCode()).build();

        } catch (UserDeactivatedException e) {
            return Response.ok().status(Response.Status.NOT_ACCEPTABLE.getStatusCode()).build();
        }
    }

    @DELETE
    @RolesAllowed({"USER"})
    @Path("followed/{followedId}")
    public Response unfollowUser(
        @PathParam("followedId") UUID followedId,
        @HeaderParam("X-User-ID") UUID followerId
    ) {
        userService.unfollowUser(followedId, followerId);
        return Response.ok(new UserHasAlreadyUnfollowedResponse()).status(Response.Status.BAD_REQUEST).build();
    }
}