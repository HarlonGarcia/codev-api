package com.codev.api.resource;

import com.codev.domain.dto.form.CategoryDTOForm;
import com.codev.domain.dto.view.CategoryDTOView;
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
    @Path("/create-category")
    public Response createCategory(CategoryDTOForm categoryDTOForm) {
        return Response.ok(new CategoryDTOView(categoryService.createCategory(categoryDTOForm))).build();
    }

    @PUT
    @Path("/{categoryId}/update-category")
    public Response updateCategory(
            @PathParam("categoryId") Long categoryId,
            CategoryDTOForm categoryDTOForm
    ) {
        return Response.ok(new CategoryDTOView(categoryService.updateCategory(categoryId, categoryDTOForm))).build();
    }

    @DELETE
    @Path("/{categoryId}/delete-category")
    public Response deleteCategory(@PathParam("categoryId") Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return Response.ok().build();
        //todo: adicionar cascade
    }

}