package com.codev.domain.repository;

import com.codev.domain.model.Challenge;

import java.sql.SQLException;
import java.util.List;

public interface ChallengeRepository {

    List<Challenge> findAllChallengesWithPaging(Integer page, Integer size);

    boolean joinChallenge(Long challengeId, Long participantId) throws SQLException;

}