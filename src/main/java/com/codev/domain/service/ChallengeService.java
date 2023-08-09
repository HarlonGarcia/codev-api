package com.codev.domain.service;

import com.codev.domain.dto.form.ChallengeDTOForm;
import com.codev.domain.dto.view.ChallengeDTOView;
import com.codev.domain.exceptions.challenges.CategoryAlreadyExistsInChallenge;
import com.codev.domain.exceptions.challenges.JoinNotAcceptedException;
import com.codev.domain.exceptions.challenges.UnjoinNotAcceptedException;
import com.codev.domain.model.Category;
import com.codev.domain.model.Challenge;
import com.codev.domain.model.Technology;
import com.codev.domain.model.User;
import com.codev.domain.repository.ChallengeRepository;
import com.codev.utils.GlobalConstants;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ChallengeService {

    @Inject
    ChallengeRepository challengeRepository;

    public List<Challenge> findAllChallengesWithPaging(Integer page, Integer size) {
        return challengeRepository.findAllChallengesWithPaging(page, size);
    }

    public Challenge findById(UUID challengeId) {
        Challenge challenge = Challenge.findById(challengeId);

        if (challenge == null)
            throw new EntityNotFoundException("Challenge not found");

        return challenge;
    }

    public List<Technology> findAllTechnologiesByChallengeId(UUID challengeId) {
        return challengeRepository.findAllTechnologiesByChallengeId(challengeId);
    }

    public List<ChallengeDTOView> findAllChallengesByCategoryId(UUID categoryId, Integer page, Integer size) {
        return challengeRepository.findAllChallengesByCategoryId(categoryId, page, size)
                .stream()
                .map(ChallengeDTOView::new)
                .toList();
    }

    @Transactional
    public Challenge createChallenge(ChallengeDTOForm challengeDTOForm) {
        User author = User.findById(challengeDTOForm.getAuthorId());

        if (author == null)
            throw new EntityNotFoundException("Author not found with id " + challengeDTOForm.getAuthorId());

        Challenge challenge = new Challenge(challengeDTOForm);

        if (challengeDTOForm.getCategoryId() != null) {
            Category category = Category.findById(challengeDTOForm.getCategoryId());
            if (category == null)
                throw new EntityNotFoundException("Category not found with id " + challengeDTOForm.getCategoryId());

            challenge.setCategory(category);
        }

        challenge.setAuthor(author);

        challenge.persist();
        return challenge;
    }

    @Transactional
    public Challenge updateChallenge(UUID challengeId, ChallengeDTOForm challengeDTOForm) throws InvocationTargetException, IllegalAccessException {
        Challenge challenge = findById(challengeId);
        BeanUtils.copyProperties(challenge, challengeDTOForm);

        if (challengeDTOForm.getCategoryId() != null) {
            Category category = Category.findById(challengeDTOForm.getCategoryId());
            if (category == null)
                throw new EntityNotFoundException("Category not found with id " + challengeDTOForm.getCategoryId());

            challenge.setCategory(category);
        }

        challenge.persist();
        return challenge;
    }
    
    @Transactional
    public void deactivateChallenge(UUID challengeId) {
        Challenge challenge = Challenge.findById(challengeId);

        if (challenge == null)
            throw new EntityNotFoundException();

        challenge.setActive(GlobalConstants.DEACTIVATE);
        challenge.persist();
    }

    @Transactional
    public boolean joinChallenge(UUID challengeId, UUID participantId) throws JoinNotAcceptedException {
        return challengeRepository.joinChallenge(challengeId, participantId);
    }

    @Transactional
    public boolean unjoinChallenge(UUID challengeId, UUID participantId) throws UnjoinNotAcceptedException {
        return challengeRepository.unjoinChallenge(challengeId, participantId);
    }

    @Transactional
    public Challenge addCategoryInChallenge(UUID challengeId, UUID categoryId) throws CategoryAlreadyExistsInChallenge, SQLException {
        Challenge challenge = Challenge.findById(challengeId);

        if (challenge == null)
            throw new EntityNotFoundException("Challenge not found with id " + challengeId);

        if (challenge.getCategory() != null)
            throw new CategoryAlreadyExistsInChallenge();

        Category category = Category.findById(categoryId);

        if (category == null)
            throw new EntityNotFoundException("Category not found with id " + category);

        challengeRepository.addCategoryInChallenge(challengeId, categoryId);

        challenge.setCategory(category);
        return challenge;
    }

    @Transactional
    public Challenge removeCategoryInChallenge(UUID challengeId) throws SQLException {
        Challenge challenge = Challenge.findById(challengeId);

        if (challenge == null)
            throw new EntityNotFoundException("Challenge not found with id " + challengeId);

        challengeRepository.removeCategoryInChallenge(challengeId);

        challenge.setCategory(null);
        return challenge;
    }

}
