package com.codev.infraestructure.impl;

import java.util.List;

import com.codev.domain.model.Category;
import com.codev.domain.model.Technology;
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
    public List<Category> findAllCategories() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Category> criteriaQuery = criteriaBuilder.createQuery(Category.class);
        Root<Category> categoryRoot = criteriaQuery.from(Category.class);

        criteriaQuery.select(categoryRoot);

        criteriaQuery.orderBy(
                criteriaBuilder.asc(categoryRoot.get("name"))
        );

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public Technology findTechnologyByCategoryName(String categoryName) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Technology> criteriaQuery = criteriaBuilder.createQuery(Technology.class);
        Root<Technology> technologyRoot = criteriaQuery.from(Technology.class);

        criteriaQuery.where(criteriaBuilder.equal(technologyRoot.get("name"), categoryName));

        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    @Override
    public void deleteCategory(Long categoryId) {

        String deleteCategoryInChallengeQuery = "UPDATE tb_challenge SET category_id = NULL WHERE category_id = :categoryId";
        entityManager.createNativeQuery(deleteCategoryInChallengeQuery)
                .setParameter("categoryId", categoryId)
                .executeUpdate();

        String deleteCategoryQuery = "DELETE FROM tb_category WHERE id = :categoryId";
        entityManager.createNativeQuery(deleteCategoryQuery)
                .setParameter("categoryId", categoryId)
                .executeUpdate();

    }

}
