package com.codev.api.resource;

import com.codev.domain.dto.view.UserDTOView;
import com.codev.domain.exceptions.users.UserDeactivatedException;
import com.codev.domain.model.User;
import com.codev.domain.service.MeService;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.UUID;

@Path("me")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Me")
@RequiredArgsConstructor
public class MeResource {

    private final MeService meService;

    @PermitAll
    @GET
    public Response findMe(@HeaderParam("Access-Token") String token) throws Exception {
        if (token == null) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        } else {
            UserDTOView user = meService.findMe(token);
            return Response.status(Response.Status.OK).entity(user).build();
        }
    }

}
