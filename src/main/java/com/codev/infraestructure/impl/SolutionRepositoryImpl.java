package com.codev.infraestructure.impl;

import com.codev.domain.dto.view.SolutionDTOView;
import com.codev.domain.model.Solution;
import com.codev.domain.repository.SolutionRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;

@ApplicationScoped
public class SolutionRepositoryImpl implements SolutionRepository {

    private final EntityManager entityManager;

    public SolutionRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<SolutionDTOView> findAllSolutionsByChallengeId(Long challengeId, Integer page, Integer size) {

        if (page < 0) {
            throw new IllegalArgumentException("Page must be a positive integer.");
        }

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<SolutionDTOView> criteriaQuery = criteriaBuilder.createQuery(SolutionDTOView.class);

        Root<Solution> solutionRoot = criteriaQuery.from(Solution.class);

        criteriaQuery.multiselect(
                solutionRoot.get("challenge").get("id"),
                solutionRoot.get("author").get("id"),
                solutionRoot.get("repositoryUrl"),
                solutionRoot.get("deployUrl"),
                criteriaBuilder.count(solutionRoot.get("authors").get("id"))
        );

        criteriaQuery.where(criteriaBuilder.equal(solutionRoot.get("challenge").get("id"), challengeId));

        criteriaQuery.groupBy(
                solutionRoot.get("challenge").get("id"),
                solutionRoot.get("author").get("id"),
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
