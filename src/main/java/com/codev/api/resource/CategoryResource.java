package com.codev.api.resource;

import com.codev.domain.service.CategoryService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("categories")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Category")
public class CategoryResource {

    @Inject
    CategoryService categoryService;

    @POST
    @Path("/{categoryName}/create-category")
    public Response createCategory(@PathParam("categoryName") String categoryName) {
        return Response.ok(categoryService.createCategory(categoryName)).build();
    }

    @DELETE
    @Path("/{categoryId}/delete-category")
    public Response deleteCategory(@PathParam("categoryId") Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return Response.ok().build();
        //todo: adicionar cascade
    }

}