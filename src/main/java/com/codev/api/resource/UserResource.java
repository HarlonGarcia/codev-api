package com.codev.api.resource;

import com.codev.domain.dto.form.UserDTOForm;
import com.codev.domain.dto.view.UserDTOView;
import com.codev.domain.model.User;
import com.codev.domain.service.UserService;
import jakarta.inject.Inject;
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
    public Response findAllUsers() {
        try {
            List<User> users = userService.findAllUsers();
            return Response.ok(users).build();
        } catch (Exception error) {
            error.printStackTrace();
            return Response.ok(error).status(400).build();
        }
    }

    @GET
    @Path("/{id}")
    public Response findUserById(@PathParam("id") Long id) {
        UserDTOView userDTOView = userService.findUserById(id);
        return Response.ok(userDTOView).build();
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
            return Response.ok(e).status(400).build();
        }
    }
}