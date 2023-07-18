package com.codev.infraestructure.impl;

import com.codev.domain.dto.view.SolutionDTOView;
import com.codev.domain.model.Solution;
import com.codev.domain.model.User;
import com.codev.domain.repository.SolutionRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;

import javax.sql.DataSource;
import java.util.List;

@ApplicationScoped
public class SolutionRepositoryImpl implements SolutionRepository {

    private final EntityManager entityManager;

    public SolutionRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Inject
    DataSource dataSource;


    @Override
    public List<SolutionDTOView> findAllSolutionsByChallengeId(Long challengeId, Integer page, Integer size) {

        if (page < 0) {
            throw new IllegalArgumentException("Page must be a positive integer.");
        }

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<SolutionDTOView> criteriaQuery = criteriaBuilder.createQuery(SolutionDTOView.class);

        Root<Solution> solutionRoot = criteriaQuery.from(Solution.class);

        Join<Solution, User> authorJoin = solutionRoot.join("author", JoinType.LEFT);
        Join<Solution, User> likeJoin = solutionRoot.join("authors", JoinType.LEFT);

        Subquery<Long> subquery = criteriaQuery.subquery(Long.class);
        Root<User> likeUserRoot = subquery.from(User.class);
        subquery.select(likeUserRoot.get("id"));
        subquery.where(
                criteriaBuilder.and(
                        criteriaBuilder.equal(likeUserRoot, authorJoin),
                        criteriaBuilder.equal(likeUserRoot.get("id"), likeJoin.get("id"))
                )
        );

        criteriaQuery.multiselect(
                solutionRoot.get("challenge").get("id"),
                authorJoin,
                solutionRoot.get("repositoryUrl"),
                solutionRoot.get("deployUrl"),
                criteriaBuilder.count(likeJoin.get("id")),
                criteriaBuilder.exists(subquery)
        );

        criteriaQuery.where(criteriaBuilder.equal(solutionRoot.get("challenge").get("id"), challengeId));

        criteriaQuery.groupBy(
                solutionRoot.get("challenge").get("id"),
                authorJoin,
                solutionRoot.get("repositoryUrl"),
                solutionRoot.get("deployUrl")
        );

        int firstResult = page * size;

        return entityManager.createQuery(criteriaQuery)
                .setFirstResult(firstResult)
                .setMaxResults(size)
                .getResultList();
    }
}
