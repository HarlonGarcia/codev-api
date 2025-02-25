package com.codev.infraestructure.impl;

import com.codev.domain.dto.generics.ItemsWithPagination;
import com.codev.domain.enums.ChallengeStatus;
import com.codev.domain.enums.Order;
import com.codev.domain.enums.OrderBy;
import com.codev.domain.exceptions.challenges.JoinNotAcceptedException;
import com.codev.domain.exceptions.challenges.UnjoinNotAcceptedException;
import com.codev.domain.model.Challenge;
import com.codev.domain.model.ChallengeTechnology;
import com.codev.domain.model.Technology;
import com.codev.domain.model.User;
import com.codev.domain.repository.ChallengeRepository;
import com.codev.utils.GlobalConstants;
import com.codev.utils.Pagination;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
        challengeRoot.fetch("image", JoinType.LEFT);

        int firstResult = page * size;

        return entityManager.createQuery(criteriaQuery)
                .setFirstResult(firstResult)
                .setMaxResults(size)
                .getResultList();

    }

    @Override
    public Challenge findChallengeById(UUID challengeId) {
        try {
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
        } catch (NoResultException e) {
            throw new EntityNotFoundException(String.format("Challenge with id %s not found", challengeId));
        }
    }

    @Override
    public void addCategoryInChallenge(UUID challengeId, UUID categoryId) throws SQLException {
        String sql = "UPDATE tb_challenge SET category_id = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
          
            statement.setObject(1, categoryId);
            statement.setObject(2, challengeId);

            statement.executeUpdate();

        } catch (SQLException e) {
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
    public List<Challenge> findUserChallenges(
        UUID userId,
        Integer page,
        Integer size
    ) {
        if (page < 0) {
            throw new IllegalArgumentException("Page must be a positive integer.");
        }

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Challenge> criteriaQuery = criteriaBuilder.createQuery(Challenge.class);

        Root<Challenge> challengeRoot = criteriaQuery.from(Challenge.class);

        Join<Challenge, User> participantJoin = challengeRoot.join("participants");

        Predicate userPredicate = criteriaBuilder.equal(participantJoin.get("id"), userId);

        criteriaQuery.select(challengeRoot)
            .where(userPredicate);

        challengeRoot.fetch("author", JoinType.LEFT)
            .fetch("labels", JoinType.LEFT);
        challengeRoot.fetch("author", JoinType.LEFT)
            .fetch("roles", JoinType.LEFT);
        challengeRoot.fetch("technologies", JoinType.LEFT);
        challengeRoot.fetch("category", JoinType.LEFT);

        int firstResult = page * size;

        return entityManager.createQuery(criteriaQuery)
            .setFirstResult(firstResult)
            .setMaxResults(size)
            .getResultList();
    }

    @SuppressWarnings("unused")
    public Challenge createChallenge(Challenge challenge) {
        UUID challengeId = UUID.randomUUID();

        String sqlChallenge = "INSERT INTO tb_challenge (id, title, author_id, description, active, status, image_id, category_id, created_at, end_date) " +
            "VALUES (:id, :title, :authorId, :description, :active, :status, :imageId, :categoryId, :createdAt, :endDate)";

        entityManager.createNativeQuery(sqlChallenge)
            .setParameter("id", challengeId)
            .setParameter("title", challenge.getTitle())
            .setParameter("authorId", challenge.getAuthor().getId())
            .setParameter("description", challenge.getDescription())
            .setParameter("active", challenge.isActive())
            .setParameter("status", challenge.getStatus().name())
            .setParameter("imageId", challenge.getImage() != null ? challenge.getImage().getId() : null)
            .setParameter("categoryId", challenge.getCategory() != null ? challenge.getCategory().getId() : null)
            .setParameter("createdAt", challenge.getCreatedAt() != null ? challenge.getCreatedAt() : LocalDateTime.now())
            .setParameter("endDate", challenge.getEndDate())
            .executeUpdate();

        if (challenge.getTechnologies() != null && !challenge.getTechnologies().isEmpty()) {
            List<UUID> technologyIds = challenge.getTechnologies()
                .stream()
                .map(Technology::getId)
                .collect(Collectors.toList());

            List<Technology> fullTechnologies = fetchTechnologiesByIds(technologyIds);
            challenge.setTechnologies(new HashSet<>(fullTechnologies));

            StringBuilder challengeTechnologiesQuery = new StringBuilder("INSERT INTO tb_challenge_technology (id, challenge_id, technology_id) VALUES ");
            int index = 0;

            for (Technology tech : fullTechnologies) {
                challengeTechnologiesQuery
                    .append("(:id")
                    .append(index)
                    .append(", :challengeId")
                    .append(index)
                    .append(", :technologyId")
                    .append(index)
                    .append("), ");
                index++;
            }

            challengeTechnologiesQuery.setLength(challengeTechnologiesQuery.length() - 2);

            var query = entityManager.createNativeQuery(challengeTechnologiesQuery.toString());

            index = 0;
            for (Technology tech : fullTechnologies) {
                query.setParameter("id" + index, UUID.randomUUID());
                query.setParameter("challengeId" + index, challengeId);
                query.setParameter("technologyId" + index, tech.getId());
                index++;
            }

            query.executeUpdate();
        }

        challenge.setId(challengeId);
        return challenge;
    }

    @SuppressWarnings("unchecked")
    private List<Technology> fetchTechnologiesByIds(List<UUID> technologyIds) {
        String sqlFetchTechnologies = "SELECT * FROM tb_technology WHERE id IN :technologyIds";
        List<Technology> technologies = entityManager.createNativeQuery(sqlFetchTechnologies, Technology.class)
            .setParameter("technologyIds", technologyIds)
            .getResultList();

        if (technologies.size() != technologyIds.size()) {
            List<UUID> foundIds = technologies.stream().map(Technology::getId).toList();
            List<UUID> missingIds = new ArrayList<>(technologyIds);
            missingIds.removeAll(foundIds);
            throw new EntityNotFoundException("Technologies not found: " + missingIds);
        }

        return technologies;
    }

    @Override
    public ItemsWithPagination<List<Challenge>> findChallenges(
        Integer page,
        Integer size,
        ChallengeStatus status,
        UUID categoryId,
        UUID technologyId,
        Order order,
        OrderBy orderBy,
        UUID authorId
    ) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Challenge> criteriaQuery = criteriaBuilder.createQuery(Challenge.class);

        Root<Challenge> challengeRoot = criteriaQuery.from(Challenge.class);
        criteriaQuery.select(challengeRoot);

        List<Predicate> predicates = new ArrayList<>();

        if (status != null) {
            predicates.add(criteriaBuilder.equal(challengeRoot.get("status"), status));
        }

        if (categoryId != null) {
            predicates.add(criteriaBuilder.equal(challengeRoot.get("category").get("id"), categoryId));
        }

        if (technologyId != null) {
            Join<Challenge, Technology> technologyJoin = challengeRoot.join("technologies");

            predicates.add(criteriaBuilder.equal(technologyJoin.get("id"), technologyId));
        }

        predicates.add(criteriaBuilder.equal(challengeRoot.get("active"), GlobalConstants.ACTIVE));

        if (authorId != null) {
            predicates.add(criteriaBuilder.equal(challengeRoot.get("author").get("id"), authorId));
        }
        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

        challengeRoot.fetch("author", JoinType.LEFT)
            .fetch("labels", JoinType.LEFT);
        challengeRoot.fetch("author", JoinType.LEFT)
            .fetch("roles", JoinType.LEFT);
        challengeRoot.fetch("technologies", JoinType.LEFT);
        challengeRoot.fetch("category", JoinType.LEFT);
        challengeRoot.fetch("image", JoinType.LEFT);
    
        switch (orderBy) {
            case CREATED_AT:
                if (order == Order.DESC) {
                    criteriaQuery.orderBy(criteriaBuilder.desc(challengeRoot.get("createdAt")));
                } else {
                    criteriaQuery.orderBy(criteriaBuilder.asc(challengeRoot.get("createdAt")));
                }
                break;
            case POPULARITY:
                Join<Challenge, User> participantsJoin = challengeRoot.join("participants", JoinType.LEFT);
                criteriaQuery.groupBy(challengeRoot);
                Expression<Long> participantCount = criteriaBuilder.count(participantsJoin);

                if (order == Order.DESC) {
                    criteriaQuery.orderBy(criteriaBuilder.desc(participantCount));
                } else {
                    criteriaQuery.orderBy(criteriaBuilder.asc(participantCount));
                }
                break;
            default:
                if (order == Order.DESC) {
                    criteriaQuery.orderBy(criteriaBuilder.desc(challengeRoot.get("title")));
                } else {
                    criteriaQuery.orderBy(criteriaBuilder.asc(challengeRoot.get("title")));
                }
        }

        TypedQuery<Challenge> query = entityManager.createQuery(criteriaQuery);
        int itemsPerPage = size != null ? size : 10;
        int currentPage = page != null && page >= 0 ? page : 0;

        if (page != null && page >= 0) {
            query.setFirstResult(currentPage * itemsPerPage);
            query.setMaxResults(itemsPerPage);
        }
    
        List<Challenge> challenges = new ArrayList<>(query.getResultList());
        Pagination pagination = new Pagination(currentPage, itemsPerPage, getTotal());

        if (page == null) {
            pagination.setPage(currentPage);
            pagination.setSize(challenges.size());
        }

        return new ItemsWithPagination<List<Challenge>>(challenges, pagination);
    }

    public Long getTotal() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> totalQuery = criteriaBuilder.createQuery(Long.class);
        Root<Challenge> root = totalQuery.from(Challenge.class);
        
        totalQuery.select(criteriaBuilder.count(root));
        
        TypedQuery<Long> totalTypedQuery = entityManager.createQuery(totalQuery);

        return totalTypedQuery.getSingleResult() - 1;
    }

    @Override
    public boolean joinChallenge(UUID challengeId, UUID participantId) throws JoinNotAcceptedException, SQLException {
        if (!joinExists(challengeId, participantId)) {
            return insertParticipant(challengeId, participantId);
        } else {
            throw new JoinNotAcceptedException();
        }
    }

    private boolean insertParticipant(UUID challengeId, UUID participantId) throws SQLException {
        String insertParticipantQuery = "INSERT INTO tb_participant (challenge_id, participant_id) VALUES (?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(insertParticipantQuery)) {

            statement.setObject(1, challengeId);
            statement.setObject(2, participantId);

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;

        }
    }

    @Override
    public boolean unjoinChallenge(UUID challengeId, UUID participantId) throws UnjoinNotAcceptedException, SQLException {
        if (joinExists(challengeId, participantId)) {
            return deleteParticipant(challengeId, participantId);
        } else {
            throw new UnjoinNotAcceptedException();
        }
    }

    private boolean joinExists(UUID challengeId, UUID participantId) throws SQLException {
        String joinExistsQuery = "SELECT COUNT(*) FROM tb_participant WHERE challenge_id = ? AND participant_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement checkStatement = connection.prepareStatement(joinExistsQuery)) {

            checkStatement.setObject(1, challengeId);
            checkStatement.setObject(2, participantId);

            try (ResultSet resultSet = checkStatement.executeQuery()) {
                return resultSet.next() && resultSet.getInt(1) > 0;
            }
        }
    }

    private boolean deleteParticipant(UUID challengeId, UUID participantId) throws SQLException {
        String deleteParticipantQuery = "DELETE FROM tb_participant WHERE challenge_id = ? AND participant_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(deleteParticipantQuery)) {

            statement.setObject(1, challengeId);
            statement.setObject(2, participantId);

            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        }
    }

}
