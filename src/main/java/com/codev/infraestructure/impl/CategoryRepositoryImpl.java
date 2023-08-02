package com.codev.infraestructure.impl;

import com.codev.domain.model.Category;
import com.codev.domain.repository.CategoryRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@ApplicationScoped
public class CategoryRepositoryImpl implements CategoryRepository {

    @Inject
    EntityManager entityManager;

    @Override
    public Category findByCategoryName(String categoryName) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Category> criteriaQuery = criteriaBuilder.createQuery(Category.class);
        Root<Category> categoryRoot = criteriaQuery.from(Category.class);

        criteriaQuery.where(criteriaBuilder.equal(categoryRoot.get("name"), categoryName));

        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    @Override
    public void deleteCategory(Long categoryId) {

        String deleteChallengeCategoryQuery = "DELETE FROM tb_challenge_category WHERE category_id = :categoryId";
        entityManager.createNativeQuery(deleteChallengeCategoryQuery)
                .setParameter("categoryId", categoryId)
                .executeUpdate();

        String deleteCategoryQuery = "DELETE FROM tb_category WHERE id = :categoryId";
        entityManager.createNativeQuery(deleteCategoryQuery)
                .setParameter("categoryId", categoryId)
                .executeUpdate();

    }

}
