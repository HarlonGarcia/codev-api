package com.codev.infraestructure.impl;

import com.codev.domain.enums.OrderBy;
import com.codev.domain.exceptions.challenges.JoinNotAcceptedException;
import com.codev.domain.exceptions.challenges.UnjoinNotAcceptedException;
import com.codev.domain.model.Challenge;
import com.codev.domain.model.ChallengeTechnology;
import com.codev.domain.model.Technology;
import com.codev.domain.repository.ChallengeRepository;
import com.codev.utils.GlobalConstants;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

@ApplicationScoped
@RequiredArgsConstructor
public class ChallengeRepositoryImpl implements ChallengeRepository {

    @PersistenceContext
    EntityManager entityManager;

    private final DataSource dataSource;

    @Override
    public List<Technology> findAllTechnologiesByChallengeId(UUID challengeId) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Technology> criteriaQuery = criteriaBuilder.createQuery(Technology.class);

        Root<ChallengeTechnology> challengeTechnologyRoot = criteriaQuery.from(ChallengeTechnology.class);

        criteriaQuery.select(challengeTechnologyRoot.get("technology"));

        criteriaQuery.where(
                criteriaBuilder.equal(challengeTechnologyRoot.get("challenge").get("id"), challengeId)
        );

        return entityManager.createQuery(criteriaQuery)
                .getResultList();

    }

    @Override
    public List<Challenge> findAllChallengesByCategoryId(UUID categoryId, Integer page, Integer size) {

        if (page < 0) {
            throw new IllegalArgumentException("Page must be a positive integer.");
        }

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Challenge> criteriaQuery = criteriaBuilder.createQuery(Challenge.class);

        Root<Challenge> challengeRoot = criteriaQuery.from(Challenge.class);

        criteriaQuery.select(challengeRoot);

        criteriaQuery.where(
                criteriaBuilder.equal(challengeRoot.get("category").get("id"), categoryId),
                criteriaBuilder.equal(challengeRoot.get("active"), GlobalConstants.ACTIVE)
        );

        challengeRoot.fetch("author", JoinType.LEFT);

        int firstResult = page * size;

        return entityManager.createQuery(criteriaQuery)
                .setFirstResult(firstResult)
                .setMaxResults(size)
                .getResultList();

    }

    @Override
    public Challenge findChallengeById(UUID challengeId) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Challenge> criteriaQuery = criteriaBuilder.createQuery(Challenge.class);

        Root<Challenge> challengeRoot = criteriaQuery.from(Challenge.class);

        criteriaQuery.select(challengeRoot);

        criteriaQuery.where(
            criteriaBuilder.equal(challengeRoot.get("id"), challengeId),
            criteriaBuilder.equal(challengeRoot.get("active"), GlobalConstants.ACTIVE)
        );

        challengeRoot.fetch("author", JoinType.LEFT)
            .fetch("labels", JoinType.LEFT);
        challengeRoot.fetch("author", JoinType.LEFT)
            .fetch("roles", JoinType.LEFT);
        challengeRoot.fetch("technologies", JoinType.LEFT);
        challengeRoot.fetch("category", JoinType.LEFT);

        return entityManager.createQuery(criteriaQuery)
            .getSingleResult();

    }

    @Override
    public void addCategoryInChallenge(UUID challengeId, UUID categoryId) throws SQLException {
        System.out.println("boramoreno");
        String sql = "UPDATE tb_challenge SET category_id = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
          
            statement.setObject(1, categoryId);
            statement.setObject(2, challengeId);

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("eeeeeeeeeeeeeeeeeepa");
            throw new SQLException("Unable to add category in challenge");
        }
    }

    @Override
    public void removeCategoryInChallenge(UUID challengeId) throws SQLException  {
        String sql = "UPDATE tb_challenge SET category_id = NULL WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setObject(1, challengeId);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new SQLException("Unable to remove category in challenge");
        }
    }

    @Override
    public Set<Challenge> findAllChallengeWithPagingByCategoryId(
        Integer page, Integer size, UUID categoryId, OrderBy orderBy
    ) {
        if (page < 0) {
            throw new IllegalArgumentException("Page must be a positive integer.");
        }
        
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Challenge> criteriaQuery = criteriaBuilder.createQuery(Challenge.class);

        Root<Challenge> challengeRoot = criteriaQuery.from(Challenge.class);
        
        criteriaQuery.select(challengeRoot);
        
        if (orderBy == OrderBy.LATEST) {
            criteriaQuery.orderBy(criteriaBuilder.desc(
                challengeRoot.get("createdAt")
            ));
        }
        
        List<Predicate> predicates = new ArrayList<>();
        
        if (categoryId != null) {
            predicates.add(criteriaBuilder.equal(
                challengeRoot.get("category").get("id"), 
                categoryId));
        }

        predicates.add(criteriaBuilder.equal(challengeRoot.get("active"), GlobalConstants.ACTIVE));
        
        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

        challengeRoot.fetch("author", JoinType.LEFT)
            .fetch("labels", JoinType.LEFT);
        challengeRoot.fetch("author", JoinType.LEFT)
            .fetch("roles", JoinType.LEFT);
        challengeRoot.fetch("technologies", JoinType.LEFT);
        challengeRoot.fetch("category", JoinType.LEFT);

        int firstResult = page * size;

        return new HashSet<>(entityManager.createQuery(criteriaQuery)
                .setFirstResult(firstResult)
                .setMaxResults(size)
                .getResultList());
    }

    @Override
    public boolean joinChallenge(UUID challengeId, UUID participantId) throws JoinNotAcceptedException {
        String sql = "INSERT INTO tb_participant (challenge_id, participant_id) values (?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
          
            statement.setObject(1, challengeId);
            statement.setObject(2, participantId);

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            throw new JoinNotAcceptedException();
        }
    }

    @Override
    public boolean unjoinChallenge(UUID challengeId, UUID participantId) throws UnjoinNotAcceptedException {

        String sql = "DELETE FROM tb_participant WHERE challenge_id = ? AND participant_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setObject(1, challengeId);
            statement.setObject(2, participantId);

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            throw new UnjoinNotAcceptedException();
        }
    }

}
