package com.codev.infraestructure.impl;

import com.codev.domain.model.User;
import com.codev.domain.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;

import java.util.List;

@ApplicationScoped
public class UserRepositoryImpl implements UserRepository {

    private final EntityManager entityManager;

    public UserRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<User> findAllUsers() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);

        Root<User> userRoot = criteriaQuery.from(User.class);
        userRoot.fetch("roles", JoinType.LEFT);

        criteriaQuery.select(userRoot).distinct(true);

        criteriaQuery.orderBy(
                criteriaBuilder.asc(userRoot.get("id"))
        );

        return entityManager.createQuery(criteriaQuery)
                .getResultList();
    }
}
