package com.codev.api.resource;

import com.codev.domain.dto.form.TechnologyDTOForm;
import com.codev.domain.dto.view.TechnologyDTOView;
import com.codev.domain.service.TechnologyService;
import io.quarkus.cache.CacheInvalidateAll;
import io.quarkus.cache.CacheResult;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.UUID;

@Path("technologies")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Technology")
@RequiredArgsConstructor
public class TechnologyResource {

    private final TechnologyService technologyService;

    @GET
    @CacheResult(cacheName = "technology-cache")
    @RolesAllowed({"ADMIN", "USER"})
    public Response findAllTechnologies() {
        List<TechnologyDTOView> technologies = technologyService.findAllTechnologies()
            .stream().map(TechnologyDTOView::new).toList();
        
        return Response.ok(technologies).build();
    }

    @POST
    @CacheInvalidateAll(cacheName = "technology-cache")
    @RolesAllowed({"ADMIN"})
    public Response createTechnology(@Valid TechnologyDTOForm technologyDTOForm) {
        return Response.ok(new TechnologyDTOView(technologyService.createTechnology(technologyDTOForm))).build();
    }

    @PUT
    @CacheInvalidateAll(cacheName = "technology-cache")
    @RolesAllowed({"ADMIN"})
    @Path("{technologyId}")
    public Response updateTechnology(
            @PathParam("technologyId") UUID technologyId,
            @Valid TechnologyDTOForm technologyDTOForm
    ) {
        return Response.ok(new TechnologyDTOView(technologyService.updateTechnology(technologyId, technologyDTOForm))).build();
    }

    @DELETE
    @RolesAllowed({"ADMIN"})
    @Path("/{technologyId}")
    public Response deleteTechnology(@PathParam("technologyId") UUID technologyId) {
        technologyService.deleteTechnology(technologyId);
        return Response.ok().build();
    }

}