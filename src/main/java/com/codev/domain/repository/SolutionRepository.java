package com.codev.domain.repository;

import com.codev.domain.dto.view.LikeDTOView;
import com.codev.domain.dto.view.SolutionDTOView;
import com.codev.domain.exceptions.solutions.LikeNotAcceptedException;

import java.util.List;

public interface SolutionRepository {
    List<SolutionDTOView> findAllSolutionsByChallengeId(Long challengeId, Long userId, Integer page, Integer size);
    LikeDTOView addLike(Long solutionId, Long userId) throws LikeNotAcceptedException;

    LikeDTOView removeLike(Long solutionId, Long userId) throws LikeNotAcceptedException;
}
