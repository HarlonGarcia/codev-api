package com.codev.domain.repository;

import com.codev.domain.dto.view.SolutionDTOView;

import java.util.List;

public interface SolutionRepository {
    List<SolutionDTOView> findAllSolutionsByChallengeId(Long challengeId, Integer page, Integer size);
}
