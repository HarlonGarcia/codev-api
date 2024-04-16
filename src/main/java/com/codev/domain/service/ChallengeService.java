package com.codev.domain.service;

import com.codev.domain.dto.form.ChallengeDTOForm;
import com.codev.domain.dto.view.ChallengeDTOView;
import com.codev.domain.enums.ChallengeStatus;
import com.codev.domain.enums.OrderBy;
import com.codev.domain.exceptions.challenges.CategoryExistsInChallengeException;
import com.codev.domain.exceptions.challenges.JoinNotAcceptedException;
import com.codev.domain.exceptions.challenges.UnjoinNotAcceptedException;
import com.codev.domain.model.Category;
import com.codev.domain.model.Challenge;
import com.codev.domain.model.Technology;
import com.codev.domain.model.User;
import com.codev.domain.repository.ChallengeRepository;
import com.codev.utils.GlobalConstants;
import io.quarkus.cache.Cache;
import io.quarkus.cache.CacheName;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
@RequiredArgsConstructor
public class ChallengeService {

    @Inject
    @CacheName("technology-cache")
    Cache cache;

    private final ChallengeRepository challengeRepository;

    public Set<ChallengeDTOView> findAllChallengeWithPagingByCategoryId(Integer page, Integer size, UUID categoryId, OrderBy orderBy) {
        
        return challengeRepository.findAllChallengeWithPagingByCategoryId(page, size, categoryId, orderBy)
            .stream()
            .map(ChallengeDTOView::new)
            .collect(Collectors.toSet());

    }

    public Challenge findChallengeById(UUID challengeId) {
        return challengeRepository.findChallengeById(challengeId);
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
    public ChallengeDTOView createChallenge(ChallengeDTOForm challengeDTOForm) {
        User author = User.findById(challengeDTOForm.getAuthorId());

        if (author == null)
            throw new EntityNotFoundException(String.format("Author with id %s does not exist", challengeDTOForm.getAuthorId()));

        if (challengeDTOForm.getStatus() == null) {
            challengeDTOForm.setStatus(ChallengeStatus.TO_BEGIN);
        }

        Challenge challenge = getChallenge(challengeDTOForm, author);

        return new ChallengeDTOView(challenge);
    }

    private static Challenge getChallenge(ChallengeDTOForm challengeDTOForm, User author) {
        Challenge challenge = new Challenge(challengeDTOForm);

        UUID categoryId = challengeDTOForm.getCategoryId();

        if (categoryId != null) {
            Category category = Category.findById(categoryId);

            if (category == null) {
                throw new EntityNotFoundException(String.format("Category with id %s does not exist", challengeDTOForm.getCategoryId()));
            }

            challenge.setCategory(category);
        }

        challenge.setAuthor(author);

        challenge.persist();
        return challenge;
    }

    @Transactional
    public Challenge updateChallenge(UUID challengeId, ChallengeDTOForm challengeDTOForm) throws InvocationTargetException, IllegalAccessException {
        Challenge challenge = Challenge.findById(challengeId);
        BeanUtils.copyProperties(challenge, challengeDTOForm);

        if (challengeDTOForm.getCategoryId() != null) {
            Category category = Category.findById(challengeDTOForm.getCategoryId());
            if (category == null)
                throw new EntityNotFoundException(String.format("Category with id %s does not exist", challengeDTOForm.getCategoryId()));

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
    public Challenge addCategoryInChallenge(UUID challengeId, UUID categoryId) throws CategoryExistsInChallengeException, SQLException {
        Challenge challenge = Challenge.findById(challengeId);

        if (challenge == null)
            throw new EntityNotFoundException(String.format("Challenge with id %s does not exist", challengeId));

        if (challenge.getCategory() != null)
            throw new CategoryExistsInChallengeException();

        Category category = Category.findById(categoryId);

        if (category == null)
            throw new EntityNotFoundException(String.format("Challenge with id %s does not exist", challengeId));

        challengeRepository.addCategoryInChallenge(challengeId, categoryId);

        challenge.setCategory(category);
        return challenge;
    }

    @Transactional
    public Challenge removeCategoryInChallenge(UUID challengeId) throws SQLException {
        Challenge challenge = Challenge.findById(challengeId);

        if (challenge == null)
            throw new EntityNotFoundException(String.format("Challenge with id %s does not exist", challengeId));

        challengeRepository.removeCategoryInChallenge(challengeId);

        challenge.setCategory(null);
        return challenge;
    }

}
