package com.codev.domain.service;

import com.codev.domain.dto.view.SolutionDTOView;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class SolutionService {

    @Transactional
    public List<SolutionDTOView> findAllSolutionsByChallengeId(Long challengeId) {
        return new ArrayList<>();
    }
}
