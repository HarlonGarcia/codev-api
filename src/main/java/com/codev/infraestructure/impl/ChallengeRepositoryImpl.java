package com.codev.infraestructure.impl;

import com.codev.domain.dto.view.ChallengeDTOView;
import com.codev.domain.model.Challenge;
import com.codev.domain.model.UserChallenge;
import com.codev.domain.repository.ChallengeRepository;
import com.codev.utils.GlobalConstants;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;

import java.util.List;

@ApplicationScoped
public class ChallengeRepositoryImpl implements ChallengeRepository {

    private final EntityManager entityManager;

    public ChallengeRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<ChallengeDTOView> findAllChallengesWithLikesAndPaging(Integer page, Integer size) {

        if (page < 0) {
            throw new IllegalArgumentException("Page must be a positive integer.");
        }

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ChallengeDTOView> criteriaQuery = criteriaBuilder.createQuery(ChallengeDTOView.class);

        Root<Challenge> challengeRoot = criteriaQuery.from(Challenge.class);
        Join<Challenge, UserChallenge> userChallengeJoin = challengeRoot.join("userChallenges", JoinType.LEFT);

        criteriaQuery.multiselect(
                challengeRoot.get("id"),
                challengeRoot.get("title"),
                challengeRoot.get("description"),
                criteriaBuilder.count(userChallengeJoin.get("like"))
        );

        criteriaQuery.where(
                criteriaBuilder.equal(challengeRoot.get("status"), GlobalConstants.ACTIVE)
        );

        criteriaQuery.groupBy(
                challengeRoot.get("id"),
                challengeRoot.get("title"),
                challengeRoot.get("description")
        );

        int firstResult = page * size;

        return entityManager.createQuery(criteriaQuery)
                .setFirstResult(firstResult)
                .setMaxResults(size)
                .getResultList();
    }
}
