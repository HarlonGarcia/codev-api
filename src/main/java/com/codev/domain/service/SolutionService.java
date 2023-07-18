package com.codev.domain.service;

import com.codev.domain.dto.form.SolutionDTOForm;
import com.codev.domain.dto.view.SolutionDTOView;
import com.codev.domain.model.Challenge;
import com.codev.domain.model.Solution;
import com.codev.domain.model.User;
import com.codev.domain.repository.SolutionRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class SolutionService {

    @Inject
    SolutionRepository solutionRepository;

    @Inject
    ChallengeService challengeService;

    @Inject
    UserService userService;

    public List<SolutionDTOView> findAllSolutionsByChallengeId(Long challengeId, Integer page, Integer size) {
        return solutionRepository.findAllSolutionsByChallengeId(challengeId, page, size);
    }

    @Transactional
    public SolutionDTOView createSolution(Long challengeId, SolutionDTOForm solutionDTOForm) {
        Challenge challenge = challengeService.findById(challengeId);
        User author = User.findById(solutionDTOForm.getAuthorId());
        if (author == null)
            throw new EntityNotFoundException("Author not found");

        Solution solution = new Solution(solutionDTOForm);
        solution.setChallenge(challenge);
        solution.setAuthor(author);

        Solution.persist(solution);
        return new SolutionDTOView(solution);
    }
}
