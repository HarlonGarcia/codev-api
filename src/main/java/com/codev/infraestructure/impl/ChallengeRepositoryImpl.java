package com.codev.infraestructure.impl;

import com.codev.domain.model.Category;
import com.codev.domain.model.Challenge;
import com.codev.domain.model.ChallengeCategory;
import com.codev.domain.repository.ChallengeRepository;
import com.codev.utils.GlobalConstants;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@ApplicationScoped
public class ChallengeRepositoryImpl implements ChallengeRepository, PanacheRepository<Category> {

    private final EntityManager entityManager;

    public ChallengeRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Inject
    DataSource dataSource;

    @Override
    public List<Category> findAllCategoriesByChallengeId(Long challengeId) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Category> criteriaQuery = criteriaBuilder.createQuery(Category.class);

        Root<ChallengeCategory> challengeCategoryRoot = criteriaQuery.from(ChallengeCategory.class);

        criteriaQuery.select(challengeCategoryRoot.get("category"));

        criteriaQuery.where(
                criteriaBuilder.equal(challengeCategoryRoot.get("challenge").get("id"), challengeId)
        );

        return entityManager.createQuery(criteriaQuery)
                .getResultList();

    }

    @Override
    public List<Challenge> findAllChallengesWithPaging(Integer page, Integer size) {

        if (page < 0) {
            throw new IllegalArgumentException("Page must be a positive integer.");
        }

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Challenge> criteriaQuery = criteriaBuilder.createQuery(Challenge.class);

        Root<Challenge> challengeRoot = criteriaQuery.from(Challenge.class);

        criteriaQuery.select(challengeRoot);

        criteriaQuery.where(
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
    public boolean joinChallenge(Long challengeId, Long participantId) throws SQLException {
        String sql = "INSERT INTO tb_participant (challenge_id, participant_id) values (?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, challengeId);
            statement.setLong(2, participantId);

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            throw new SQLException();
        } // status code 406 igual foi feito no add like
    }

    @Override
    public boolean unjoinChallenge(Long challengeId, Long participantId) throws SQLException {
        String sql = "DELETE FROM tb_participant WHERE challenge_id = ? AND participant_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, challengeId);
            statement.setLong(2, participantId);

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            throw new SQLException();
        } // status code 406 igual foi feito no add like
    }

}
