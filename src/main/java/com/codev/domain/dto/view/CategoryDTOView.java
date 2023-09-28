package com.codev.domain.dto.view;

import com.codev.domain.model.Category;
import com.codev.utils.helpers.DtoTransformer;
import lombok.Data;

import java.util.UUID;

@Data
public class CategoryDTOView {

    private UUID id;

    private String name;

    public CategoryDTOView(Category category){
        DtoTransformer<Category, CategoryDTOView> transform = new DtoTransformer<>();
        transform.copyProperties(category, this);
    }

    public CategoryDTOView() {}

}
