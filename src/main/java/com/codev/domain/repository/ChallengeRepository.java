package com.codev.domain.repository;

import com.codev.domain.model.Technology;
import com.codev.domain.model.Challenge;

import java.sql.SQLException;
import java.util.List;

public interface ChallengeRepository {

    List<Challenge> findAllChallengesWithPaging(Integer page, Integer size);

    boolean joinChallenge(Long challengeId, Long participantId) throws SQLException;

    boolean unjoinChallenge(Long challengeId, Long participantId) throws SQLException;

    List<Technology> findAllTechnologiesByChallengeId(Long challengeId);

}