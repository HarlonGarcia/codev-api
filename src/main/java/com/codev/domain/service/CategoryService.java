package com.codev.domain.service;

import com.codev.domain.model.Category;
import com.codev.domain.repository.CategoryRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class CategoryService {

    @Inject
    CategoryRepository categoryRepository;

    @Transactional
    public Category createCategory(String categoryName) {
        Category category = new Category(categoryName);
        category.persist();
        return category;
    }

    @Transactional
    public void deleteCategory(Long categoryId) {
        Category category = Category.findById(categoryId);
        if (category == null)
            throw new EntityNotFoundException("Category does not exist and therefore it was not possible to delete");

        categoryRepository.deleteCategory(categoryId);
    }
}
