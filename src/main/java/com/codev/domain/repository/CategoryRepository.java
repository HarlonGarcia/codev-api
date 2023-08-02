package com.codev.domain.repository;

import com.codev.domain.model.Category;

public interface CategoryRepository {

    Category findByCategoryName(String categoryName);

    void deleteCategory(Long categoryId);

}
