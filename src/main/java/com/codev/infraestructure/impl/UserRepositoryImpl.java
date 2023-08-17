package com.codev.infraestructure.impl;

import com.codev.domain.dto.form.UserFiltersDTOForm;
import com.codev.domain.model.User;
import com.codev.domain.repository.UserRepository;
import com.codev.utils.GlobalConstants;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class UserRepositoryImpl implements UserRepository {

    private final EntityManager entityManager;

    public UserRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<User> findAllUsers(UserFiltersDTOForm filters) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);

        Root<User> userRoot = criteriaQuery.from(User.class);
        userRoot.fetch("roles", JoinType.LEFT);

        List<Predicate> predicates = new ArrayList<>();

        if (filters.getStartsWith() != null) {
            String name = filters.getStartsWith().toLowerCase();

            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(userRoot.get("name")), "%" + name + "%"));
        } else
            criteriaQuery.select(userRoot).distinct(true);

        predicates.add(criteriaBuilder.equal(userRoot.get("active"), GlobalConstants.ACTIVE));

        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

        criteriaQuery.orderBy(
                criteriaBuilder.asc(userRoot.get("id"))
        );

        return entityManager.createQuery(criteriaQuery)
                .getResultList();
    }

    @Override
    public User findByUsername(String email) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);

        Root<User> userRoot = criteriaQuery.from(User.class);
        userRoot.fetch("roles", JoinType.LEFT);

        criteriaQuery.where(
                criteriaBuilder.equal(userRoot.get("email"), email),
                criteriaBuilder.equal(userRoot.get("active"), GlobalConstants.ACTIVE)
        );

        return entityManager.createQuery(criteriaQuery)
                .getSingleResult();
    }

    @Override
    public User findById(UUID userId) {
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);

            Root<User> userRoot = criteriaQuery.from(User.class);
            userRoot.fetch("roles", JoinType.LEFT);
            userRoot.fetch("labels", JoinType.LEFT);

            criteriaQuery.where(
                criteriaBuilder.equal(userRoot.get("id"), userId)
            );

            return entityManager.createQuery(criteriaQuery)
                .getSingleResult();
        } catch (NoResultException e) {
            throw new EntityNotFoundException("User not found");
        }
    }

}
