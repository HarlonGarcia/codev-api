package com.codev.infraestructure.impl;

import com.codev.domain.repository.TechnologyRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class TechnologyRepositoryImpl implements TechnologyRepository {

    @Inject
    EntityManager entityManager;

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
