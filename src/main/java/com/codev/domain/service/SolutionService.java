package com.codev.domain.service;

import com.codev.domain.dto.view.SolutionDTOView;
import com.codev.domain.repository.SolutionRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class SolutionService {

    @Inject
    SolutionRepository solutionRepository;

    public List<SolutionDTOView> findAllSolutionsByChallengeId(Long challengeId, Integer page, Integer size) {
        return solutionRepository.findAllSolutionsByChallengeId(challengeId, page, size);
    }
}
