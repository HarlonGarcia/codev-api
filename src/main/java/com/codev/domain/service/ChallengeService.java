package com.codev.domain.service;

import com.codev.domain.dto.form.ChallengeDTOForm;
import com.codev.domain.model.Challenge;
import com.codev.domain.model.Solution;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class ChallengeService {

    public List<Challenge> findAllChallengeWithPaging(Integer page, Integer size) {
         return Challenge.findAll().page(page, size).list();
    }

    @Transactional
    public Challenge createChallenge(ChallengeDTOForm challengeDTOForm) {
        Challenge challenge = new Challenge(challengeDTOForm);
        Solution solution1 = new Solution(1L, challenge, "", "");
        Solution solution2 = new Solution(2L, challenge, "", "");
        challenge.getSolutions().add(solution1);
        challenge.getSolutions().add(solution2);
        challenge.persist();
        return challenge;
    }
}
