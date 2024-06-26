package com.codev.infraestructure.impl;

import com.codev.domain.model.Technology;
import com.codev.domain.repository.TechnologyRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class TechnologyRepositoryImpl implements TechnologyRepository {

    @PersistenceContext
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
    public void deleteTechnology(UUID technologyId) {

        String deleteChallengeTechnologyQuery = "DELETE FROM tb_challenge_technology WHERE technology_id = :technologyId";
        entityManager.createNativeQuery(deleteChallengeTechnologyQuery)
                .setParameter("technologyId", technologyId)
                .executeUpdate();

        String deleteTechnologyQuery = "DELETE FROM tb_technology WHERE id = :technologyId";
        entityManager.createNativeQuery(deleteTechnologyQuery)
                .setParameter("technologyId", technologyId)
                .executeUpdate();

    }

    @Override
    public boolean existsByName(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Technology> technologyRoot = criteriaQuery.from(Technology.class);

        Predicate namePredicate = criteriaBuilder.equal(technologyRoot.get("name"), name);
        criteriaQuery.where(namePredicate);

        criteriaQuery.select(criteriaBuilder.count(technologyRoot));

        TypedQuery<Long> query = entityManager.createQuery(criteriaQuery);
        try {
            return query.getSingleResult() > 0;
        } catch (NoResultException e) {
            return false;
        }
    }


}
