package com.codev.api.resource;

import com.codev.domain.dto.form.ImageDTOForm;
import com.codev.domain.model.Image;
import com.codev.domain.service.ImageService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Optional;

@Path("images")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ImageResource {

    @Inject
    ImageService imageService;

    @POST
    public Response uploadImage(ImageDTOForm image) {
        Image savedImage = imageService.saveImage(image);
        return Response.ok(savedImage).build();
    }

    @GET
    @Path("/{id}")
    public Response getImage(@PathParam("id") Long id) {
        Optional<Image> imageOpt = imageService.getImageById(id);
        if (imageOpt.isPresent()) {
            return Response.ok(imageOpt.get()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteImage(@PathParam("id") Long id) {
        imageService.deleteImage(id);
        return Response.noContent().build();
    }
}

