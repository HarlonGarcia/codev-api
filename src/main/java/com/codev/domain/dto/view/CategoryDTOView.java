package com.codev.domain.dto.view;

import com.codev.domain.model.Category;
import lombok.Data;

import java.util.UUID;

@Data
public class CategoryDTOView {

    private UUID id;

    private String name;

    public CategoryDTOView(Category category){
        this.id = category.getId();
        this.name = category.getName();
    }

}
