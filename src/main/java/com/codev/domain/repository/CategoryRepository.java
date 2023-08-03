package com.codev.domain.repository;

import com.codev.domain.model.Technology;

public interface CategoryRepository {

    Technology findByCategoryName(String categoryName);

    void deleteCategory(Long categoryId);

}
