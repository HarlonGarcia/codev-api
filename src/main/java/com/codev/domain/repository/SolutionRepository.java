package com.codev.domain.repository;

import com.codev.domain.dto.view.LikeDTOView;
import com.codev.domain.dto.view.SolutionDTOView;
import com.codev.domain.exceptions.solutions.LikeNotAcceptedException;
import com.codev.domain.exceptions.solutions.SolutionNotDeletedException;

import java.util.List;
import java.util.UUID;

public interface SolutionRepository {
    List<SolutionDTOView> findAllSolutionsByChallengeId(UUID challengeId, UUID userId, Integer page, Integer size);

    LikeDTOView addLike(UUID solutionId, UUID userId) throws LikeNotAcceptedException;

    LikeDTOView removeLike(UUID solutionId, UUID userId) throws LikeNotAcceptedException;

    boolean deleteSolution(UUID solutionId, UUID authorId) throws SolutionNotDeletedException;

    void removeLikeBySolutionId(UUID solutionId) throws SolutionNotDeletedException;
}
