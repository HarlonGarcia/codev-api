package com.codev.infraestructure.impl;

import com.codev.domain.model.Challenge;
import com.codev.infraestructure.query.ChallengeRepositoryQueries;
import io.quarkus.hibernate.orm.panache.PanacheQuery;

public class ChallengeRepositoryImpl implements ChallengeRepositoryQueries {

    @Override
    public PanacheQuery<Challenge> findAllChallengeWithPaging(Integer page, Integer size) {
        return null;
    }

}
