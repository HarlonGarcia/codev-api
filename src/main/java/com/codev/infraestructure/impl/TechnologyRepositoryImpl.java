package com.codev.infraestructure.impl;

import java.util.List;

import com.codev.domain.model.Technology;
import com.codev.domain.repository.TechnologyRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@ApplicationScoped
public class TechnologyRepositoryImpl implements TechnologyRepository {

    @Inject
    EntityManager entityManager;

    @Override
    public List<Technology> findAllTechnologies() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Technology> criteriaQuery = criteriaBuilder.createQuery(Technology.class);

        Root<Technology> technologyRoot = criteriaQuery.from(Technology.class);

        criteriaQuery.select(technologyRoot);

        criteriaQuery.orderBy(
                criteriaBuilder.asc(technologyRoot.get("name"))
        );
        
        return entityManager.createQuery(criteriaQuery)
                .getResultList();
    }

    @Override
    public void deleteTechnology(Long technologyId) {

        String deleteChallengeTechnologyQuery = "DELETE FROM tb_challenge_technology WHERE technology_id = :technologyId";
        entityManager.createNativeQuery(deleteChallengeTechnologyQuery)
                .setParameter("technologyId", technologyId)
                .executeUpdate();

        String deleteTechnologyQuery = "DELETE FROM tb_technology WHERE id = :technologyId";
        entityManager.createNativeQuery(deleteTechnologyQuery)
                .setParameter("technologyId", technologyId)
                .executeUpdate();

    }

}
