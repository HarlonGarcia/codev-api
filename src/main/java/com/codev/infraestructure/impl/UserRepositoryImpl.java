package com.codev.infraestructure.impl;

import com.codev.domain.dto.form.UserFiltersDTOForm;
import com.codev.domain.dto.view.UserDTOView;
import com.codev.domain.model.FollowUser;
import com.codev.domain.model.Label;
import com.codev.domain.model.User;
import com.codev.domain.repository.UserRepository;
import com.codev.utils.GlobalConstants;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    EntityManager entityManager;

    private final DataSource dataSource;

    @Override
    public List<User> findAllUsers(UserFiltersDTOForm filters, Integer page, Integer size) {

        if (page < 0) {
            throw new IllegalArgumentException("Page must be a positive integer.");
        }

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);

        Root<User> userRoot = criteriaQuery.from(User.class);
        userRoot.fetch("roles", JoinType.LEFT);
        userRoot.fetch("labels", JoinType.LEFT);

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

        int firstResult = page * size;

        return entityManager.createQuery(criteriaQuery)
            .setFirstResult(firstResult)
            .setMaxResults(size)
            .getResultList();
    }

    public List<UserDTOView> findAllFollowedUsers(UUID followerId, Integer page, Integer size) {

        if (page < 0) {
            throw new IllegalArgumentException("Page must be a positive integer.");
        }

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserDTOView> query = criteriaBuilder.createQuery(UserDTOView.class);

        Root<User> userRoot = query.from(User.class);
        Join<User, FollowUser> followJoin = userRoot.join("usersFollowed", JoinType.LEFT);

        Predicate followedCondition = criteriaBuilder.equal(followJoin.get("id"), followerId);

        Expression<Object> isFollowedExpression = criteriaBuilder.selectCase()
            .when(followedCondition, criteriaBuilder.literal(true))
            .otherwise(criteriaBuilder.literal(false));

        query.select(
            criteriaBuilder.construct(
                UserDTOView.class,
                userRoot.get("id"),
                userRoot.get("name"),
                userRoot.get("email"),
                userRoot.get("githubUrl"),
                userRoot.get("additionalUrl"),
                userRoot.get("createdAt"),
                userRoot.get("updatedAt"),
                isFollowedExpression.alias("following")
            )
        );

        query.where(
            followedCondition,
            criteriaBuilder.equal(userRoot.get("active"), GlobalConstants.ACTIVE)
        );

        int firstResult = page * size;

        return entityManager.createQuery(query)
            .setFirstResult(firstResult)
            .setMaxResults(size)
            .getResultList();
    }

    @Override
    public User findByEmail(String email) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);

        Root<User> userRoot = criteriaQuery.from(User.class);
        userRoot.fetch("roles", JoinType.LEFT);
        userRoot.fetch("labels", JoinType.LEFT);

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
            throw new EntityNotFoundException(String.format("User with id %s does not exist", userId));
        }
    }

    @Override
    public void createUser(User user) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "INSERT INTO tb_user " +
                "(id, active, name, email, password, created_at, updated_at, additional_url, github_url) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setObject(1, user.getId());
                statement.setBoolean(2, user.isActive());
                statement.setString(3, user.getName());
                statement.setString(4, user.getEmail());
                statement.setString(5, user.getPassword());
                statement.setTimestamp(6, Timestamp.valueOf(user.getCreatedAt()));

                statement.setTimestamp(7, Timestamp.valueOf(user.getUpdatedAt()));
                statement.setString(8, user.getAdditionalUrl());
                statement.setString(9, user.getGithubUrl());

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean followUser(UUID followedId, UUID followerId) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "INSERT INTO tb_follow_user " +
                "(followed_id, follower_id) VALUES (?, ?)";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setObject(1, followedId);
                statement.setObject(2, followerId);

                int rowsAffected = statement.executeUpdate();
                // Successful insertion
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            // If the exception is related to a unique key violation, it means that the user is already being followed.
            if (e.getMessage().contains("duplicate key value violates unique constraint \"tb_follow_user_pkey\"")) {
                return false; // Aleady being followed.
            } else {
                throw new RuntimeException(e); // Another type of exception
            }
        }
    }

    @Override
    public boolean unfollowUser(UUID followedId, UUID followerId) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "DELETE FROM tb_follow_user WHERE followed_id = ? AND follower_id = ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setObject(1, followedId);
                statement.setObject(2, followerId);

                int rowsAffected = statement.executeUpdate();
                // Successful deletion
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
