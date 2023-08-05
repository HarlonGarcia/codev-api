package com.codev.domain.repository;

import java.util.List;

import com.codev.domain.model.Category;
import com.codev.domain.model.Technology;

public interface CategoryRepository {

    List<Category> findAllCategories();
    
    Technology findTechnologyByCategoryName(String categoryName);

    void deleteCategory(Long categoryId);

}
