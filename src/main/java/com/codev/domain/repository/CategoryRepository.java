package com.codev.domain.repository;

import java.util.List;
import java.util.UUID;

import com.codev.domain.model.Category;
import com.codev.domain.model.Technology;

public interface CategoryRepository {

    List<Category> findAllCategories();
    
    Technology findTechnologyByCategoryName(String categoryName);

    void deleteCategory(UUID categoryId);

}
