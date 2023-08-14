package com.codev.api.resource;

import com.codev.domain.dto.form.CategoryDTOForm;
import com.codev.domain.dto.view.CategoryDTOView;
import com.codev.domain.service.CategoryService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.UUID;

@Path("categories")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Category")
public class CategoryResource {

    @Inject
    CategoryService categoryService;

    @RolesAllowed({"ADMIN", "USER"})
    @GET
    public Response findAllCategories() {
        List<CategoryDTOView> categories = categoryService.findAllCategories()
                .stream().map(CategoryDTOView::new).toList();

        return Response.ok(categories).build();
    }

    @RolesAllowed({"ADMIN"})
    @POST
    public Response createCategory(@Valid CategoryDTOForm categoryDTOForm) {
        return Response.ok(new CategoryDTOView(categoryService.createCategory(categoryDTOForm))).build();
    }

    @RolesAllowed({"ADMIN"})
    @PUT
    @Path("/{categoryId}")
    public Response updateCategory(
            @PathParam("categoryId") UUID categoryId,
            @Valid CategoryDTOForm categoryDTOForm
    ) {
        return Response.ok(new CategoryDTOView(categoryService.updateCategory(categoryId, categoryDTOForm))).build();
    }

    @RolesAllowed({"ADMIN"})
    @DELETE
    @Path("/{categoryId}")
    public Response deleteCategory(@PathParam("categoryId") UUID categoryId) {
        categoryService.deleteCategory(categoryId);
        return Response.ok().build();
    }

}