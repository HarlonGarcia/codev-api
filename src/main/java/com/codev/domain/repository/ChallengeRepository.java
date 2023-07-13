package com.codev.domain.repository;

import com.codev.domain.dto.view.ChallengeDTOView;

import java.util.List;

public interface ChallengeRepository {

    List<ChallengeDTOView> findAllChallengesWithPaging(Integer page, Integer size);
}