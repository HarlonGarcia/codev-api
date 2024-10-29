package com.codev.domain.repository;

import com.codev.domain.enums.ChallengeStatus;
import com.codev.domain.enums.Order;
import com.codev.domain.enums.OrderBy;
import com.codev.domain.exceptions.challenges.JoinNotAcceptedException;
import com.codev.domain.exceptions.challenges.UnjoinNotAcceptedException;
import com.codev.domain.model.Challenge;
import com.codev.domain.model.Technology;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface ChallengeRepository {

    List<Challenge> findChallenges(
        Integer page,
        Integer size,
        ChallengeStatus status,
        UUID categoryId,
        UUID technologyId,
        Order order,
        OrderBy orderBy
    );

    boolean joinChallenge(UUID challengeId, UUID participantId) throws JoinNotAcceptedException, SQLException;

    boolean unjoinChallenge(UUID challengeId, UUID participantId) throws UnjoinNotAcceptedException, SQLException;

    Challenge findChallengeById(UUID challengeId);

    List<Technology> findAllTechnologiesByChallengeId(UUID challengeId);

    List<Challenge> findAllChallengesByCategoryId(UUID categoryId, Integer page, Integer size);

    void addCategoryInChallenge(UUID challengeId, UUID categoryId) throws SQLException;

    void removeCategoryInChallenge(UUID challengeId) throws SQLException;

    List<Challenge> findUserChallenges(UUID userId, Integer page, Integer size);
}