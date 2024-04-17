package com.codev.api.resource;

import com.codev.api.security.token.TokenUtils;
import com.codev.domain.dto.view.UserDTOView;
import com.codev.domain.service.MeService;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("me")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Me")
@RequiredArgsConstructor
public class MeResource {

    private final MeService meService;

    private final TokenUtils tokenUtils;

    @PermitAll
    @GET
    public Response findMe(@HeaderParam("Authorization") String authorizationHeader) throws Exception {
        String accessToken = tokenUtils.getAccessToken(authorizationHeader);
        UserDTOView user = meService.findMe(accessToken);
        return Response.status(Response.Status.OK).entity(user).build();
    }

}
