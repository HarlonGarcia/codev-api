package com.codev.api.resource;

import com.codev.api.security.auth.AuthRequest;
import com.codev.domain.dto.form.UserDTOForm;
import com.codev.domain.dto.form.UserFiltersDTOForm;
import com.codev.domain.dto.view.UserDTOView;
import com.codev.domain.exceptions.token.GenerateTokenExcepetion;
import com.codev.domain.exceptions.users.UnathorizedLoginMessage;
import com.codev.domain.exceptions.users.UserDeactivatedException;
import com.codev.domain.exceptions.users.UserDoesNotExistResponse;
import com.codev.domain.exceptions.users.UserHasAdminRoleException;
import com.codev.domain.model.User;
import com.codev.domain.service.UserService;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
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

    @RolesAllowed({"ADMIN"})
    @GET
    public Response findAllUsers(
            @QueryParam("startsWith") @DefaultValue("") String startsWith
    ) {
        try {
            UserFiltersDTOForm filters = new UserFiltersDTOForm(startsWith);
            List<UserDTOView> users = userService.findAllUsers(filters);
            return Response.ok(users).build();
        } catch (Exception error) {
            error.printStackTrace();
            return Response.ok(error).status(Response.Status.BAD_REQUEST).build();
        }
    }

    @RolesAllowed({"ADMIN", "USER"})
    @GET
    @Path("/{userId}")
    public Response findUserById(@PathParam("userId") UUID userId) {
        try {
            User user = userService.findUserById(userId);
            return Response.ok(new UserDTOView(user)).build();
        } catch (UserDeactivatedException | EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @PermitAll
    @POST
    public Response createUser(@Valid UserDTOForm userDTOForm) {
        try {
            UserDTOView userDTOView = userService.createUser(userDTOForm);
            return Response.ok(userDTOView).status(Response.Status.CREATED).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.ok(e).status(Response.Status.BAD_REQUEST).build();
        }
    }

    @RolesAllowed({"ADMIN"})
    @POST
    @Path("/{userId}/add-admin-role")
    public Response addAdminRoleInUser(@PathParam("userId") UUID userId) {
        try {
            UserDTOView userDTOView = userService.addAdminRoleInUser(userId);
            return Response.ok(userDTOView).build();
        } catch (UserDeactivatedException | UserHasAdminRoleException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(e.getMessage()).build();
        }
    }

    @PermitAll
    @POST
    @Path("/login")
    public Response login(AuthRequest authRequest) {
        try {
            return Response.ok(userService.login(authRequest)).build();
        } catch (UnathorizedLoginMessage e) {
            return Response.ok(e).status(Response.Status.UNAUTHORIZED).build();
        } catch (GenerateTokenExcepetion e) {
            return Response.ok(e).status(Response.Status.INTERNAL_SERVER_ERROR).build();
        } catch (NoResultException e) {
            return Response.ok(new UserDoesNotExistResponse()).status(Response.Status.NOT_FOUND).build();
        }
    }

    @RolesAllowed({"ADMIN", "USER"})
    @PUT
    @Path("/{userId}")
    public Response updateUser(
            @PathParam("userId") UUID userId,
            @Valid UserDTOForm userDTOForm) {
        try {
            UserDTOView userDTOView = userService.updateUser(userId, userDTOForm);
            return Response.ok(userDTOView).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @RolesAllowed({"ADMIN", "USER"})
    @DELETE
    @Path("/{userId}")
    public Response deactivateUser(@PathParam("userId") UUID userId) {
        try {
            userService.deactivateUser(userId);
            return Response.status(Response.Status.OK.getStatusCode()).build();
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND.getStatusCode()).entity(e.getMessage()).build();
        } catch (UserDeactivatedException e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_ACCEPTABLE.getStatusCode()).entity(e.getMessage()).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()).build();
        }
    }
}