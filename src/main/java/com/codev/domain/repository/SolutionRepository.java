package com.codev.domain.repository;

import com.codev.domain.dto.view.LikeDTOView;
import com.codev.domain.dto.view.SolutionDTOView;

import java.util.List;

public interface SolutionRepository {
    List<SolutionDTOView> findAllSolutionsByChallengeId(Long challengeId, Long userId, Integer page, Integer size);
    LikeDTOView likeOrDislikeInSolution(Long solutionId, Long userId);
}
