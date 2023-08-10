package com.codev.domain.repository;

import java.util.List;
import java.util.UUID;

import com.codev.domain.model.Category;

public interface CategoryRepository {

    List<Category> findAllCategories();

    void deleteCategory(UUID categoryId);

}
