package com.codev.domain.repository;

import com.codev.domain.dto.view.LikeDTOView;
import com.codev.domain.dto.view.SolutionDTOView;
import com.codev.domain.exceptions.solutions.LikeNotAcceptedException;
import com.codev.domain.exceptions.solutions.SolutionNotDeletedException;

import java.util.List;

public interface SolutionRepository {
    List<SolutionDTOView> findAllSolutionsByChallengeId(Long challengeId, Long userId, Integer page, Integer size);

    LikeDTOView addLike(Long solutionId, Long userId) throws LikeNotAcceptedException;

    LikeDTOView removeLike(Long solutionId, Long userId) throws LikeNotAcceptedException;

    boolean removeSolution(Long solutionId, Long authorId) throws SolutionNotDeletedException;

    void removeLikeBySolutionId(Long solutionId) throws SolutionNotDeletedException;
}
