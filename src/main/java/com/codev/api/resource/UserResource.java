package com.codev.api.resource;

import com.codev.domain.dto.form.UserDTOForm;
import com.codev.domain.dto.form.UserFiltersDTOForm;
import com.codev.domain.dto.view.UserDTOView;
import com.codev.domain.exceptions.users.UserDeactivatedException;
import com.codev.domain.exceptions.users.UserDeactivatedException;
import com.codev.domain.model.User;
import com.codev.domain.service.UserService;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Path("users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "User")
public class UserResource {

    @Inject
    UserService userService;

    @GET
    public Response findAllUsers(
            @QueryParam("startsWith") @DefaultValue("") String startsWith
    ) {
        try {
            UserFiltersDTOForm filters = new UserFiltersDTOForm(startsWith);
            List<User> users = userService.findAllUsers(filters);
            return Response.ok(users).build();
        } catch (Exception error) {
            error.printStackTrace();
            return Response.ok(error).status(400).build();
        }
    }

    @GET
    @Path("/{id}")
    public Response findUserById(@PathParam("id") Long id) {
        try {
            UserDTOView userDTOView = userService.findUserById(id);
            return Response.ok(userDTOView).build();
        } catch (UserDeactivatedException | EntityNotFoundException e) {
            return Response.status(404).entity(e.getMessage()).build();
        }
    }

    @POST
    public Response createUser(UserDTOForm userDTOForm) {
        try {
            UserDTOView userDTOView = userService.createUser(userDTOForm);
            return Response.ok(userDTOView).status(201).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.ok(e).status(400).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateUser(@PathParam("id") Long id, UserDTOForm userDTOForm) {
        try {
            UserDTOView userDTOView = userService.updateUser(id, userDTOForm);
            return Response.ok(userDTOView).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(400).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deactivateUser(@PathParam("id") Long id) {
        try {
            userService.deactivateUser(id);
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