package com.codev.domain.service;

import com.codev.domain.dto.form.ChallengeDTOForm;
import com.codev.domain.dto.view.ChallengeDTOView;
import com.codev.domain.model.Challenge;
import com.codev.domain.repository.ChallengeRepository;
import com.codev.utils.GlobalConstants;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@ApplicationScoped
public class ChallengeService {

    @Inject
    ChallengeRepository challengeRepository;

    public List<ChallengeDTOView> findAllChallengeWithPaging(Integer page, Integer size) {
        return challengeRepository.findAllChallengesWithLikesAndPaging(page, size);
    }

    @Transactional
    public Challenge createChallenge(ChallengeDTOForm challengeDTOForm) {
        Challenge challenge = new Challenge(challengeDTOForm);
        challenge.persist();
        return challenge;
    }

    @Transactional
    public Challenge updateChallenge(Long challengeId, ChallengeDTOForm challengeDTOForm) throws InvocationTargetException, IllegalAccessException {
        Challenge challenge = Challenge.findById(challengeId);

        if (challenge == null)
            throw new EntityNotFoundException();

        BeanUtils.copyProperties(challenge, challengeDTOForm);
        challenge.persist();
        return challenge;
    }
    
    @Transactional
    public void deactivateChallenge(Long challengeId) {
        Challenge challenge = Challenge.findById(challengeId);

        if (challenge == null)
            throw new EntityNotFoundException();

        challenge.setStatus(GlobalConstants.DEACTIVATE);
        challenge.persist();
    }
}
