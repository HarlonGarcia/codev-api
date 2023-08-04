package com.codev.domain.repository;

import com.codev.domain.model.Challenge;
import com.codev.domain.model.Technology;

import java.sql.SQLException;
import java.util.List;

public interface ChallengeRepository {

    List<Challenge> findAllChallengesWithPaging(Integer page, Integer size);

    boolean joinChallenge(Long challengeId, Long participantId) throws SQLException;

    boolean unjoinChallenge(Long challengeId, Long participantId) throws SQLException;

    List<Technology> findAllTechnologiesByChallengeId(Long challengeId);

    List<Challenge> findAllChallengesByCategoryId(Long categoryId, Integer page, Integer size);

    void addCategoryInChallenge(Long challengeId, Long categoryId) throws SQLException;

    void removeCategoryInChallenge(Long challengeId) throws SQLException;

}