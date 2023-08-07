package com.codev.domain.service;

import com.codev.domain.dto.form.SolutionDTOForm;
import com.codev.domain.dto.view.LikeDTOView;
import com.codev.domain.dto.view.SolutionDTOView;
import com.codev.domain.exceptions.solutions.LikeNotAcceptedException;
import com.codev.domain.exceptions.solutions.SolutionNotDeletedException;
import com.codev.domain.model.Challenge;
import com.codev.domain.model.Solution;
import com.codev.domain.model.User;
import com.codev.domain.repository.SolutionRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class SolutionService {

    @Inject
    SolutionRepository solutionRepository;

    @Inject
    ChallengeService challengeService;

    @Inject
    UserService userService;

    public List<SolutionDTOView> findAllSolutionsByChallengeId(
            Long challengeId, Long userId, Integer page, Integer size
    ) {
        return solutionRepository.findAllSolutionsByChallengeId(challengeId, userId, page, size);
    }

    @Transactional
    public SolutionDTOView createSolution(UUID challengeId, SolutionDTOForm solutionDTOForm) {
        Challenge challenge = challengeService.findById(challengeId);
        User author = User.findById(solutionDTOForm.getAuthorId());

        if (author == null || challenge == null)
            throw new EntityNotFoundException("Author not found");

        // todo Implement attributes that are missing (title, author...)
        Solution solution = new Solution(solutionDTOForm);
        solution.setChallenge(challenge);
        solution.setAuthor(author);

        Solution.persist(solution);
        return new SolutionDTOView(solution);
    }

    @Transactional
    public LikeDTOView addLike(Long solutionId, Long userId) throws LikeNotAcceptedException {
        return solutionRepository.addLike(solutionId, userId);
    }

    @Transactional
    public LikeDTOView removeLike(Long solutionId, Long userId) throws LikeNotAcceptedException {
        return solutionRepository.removeLike(solutionId, userId);
    }

    @Transactional
    public boolean deleteSolution(Long solutionId, Long authorId) throws SolutionNotDeletedException {
        solutionRepository.removeLikeBySolutionId(solutionId);
        return solutionRepository.deleteSolution(solutionId, authorId);
    }
}
