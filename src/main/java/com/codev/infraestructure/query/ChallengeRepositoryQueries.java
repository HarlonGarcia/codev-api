package com.codev.infraestructure.query;

import com.codev.domain.model.Challenge;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;


public interface ChallengeRepositoryQueries {
    PanacheQuery<Challenge> findAllChallengeWithPaging(Integer page, Integer size);
}
