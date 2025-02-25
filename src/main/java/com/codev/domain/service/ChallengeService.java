package com.codev.domain.service;

import com.codev.domain.dto.form.ChallengeDTOForm;
import com.codev.domain.dto.generics.ItemsWithPagination;
import com.codev.domain.dto.view.ChallengeDTOView;
import com.codev.domain.dto.view.UserDTOView;
import com.codev.domain.enums.ChallengeStatus;
import com.codev.domain.enums.Order;
import com.codev.domain.enums.OrderBy;
import com.codev.domain.exceptions.challenges.CategoryExistsInChallengeException;
import com.codev.domain.exceptions.challenges.JoinNotAcceptedException;
import com.codev.domain.exceptions.challenges.UnjoinNotAcceptedException;
import com.codev.domain.model.*;
import com.codev.domain.repository.ChallengeRepository;
import com.codev.domain.repository.UserRepository;
import com.codev.utils.GlobalConstants;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;

    private final UserRepository userRepository;

    private final ImageService imageService;

    public ItemsWithPagination<List<ChallengeDTOView>> findChallenges(
        Integer page,
        Integer size,
        ChallengeStatus status,
        UUID categoryId,
        UUID technologyId,
        Order order,
        OrderBy orderBy,
        UUID authorId
    ) {
        ItemsWithPagination<List<Challenge>> response = challengeRepository.findChallenges(
            page,
            size,
            status,
            categoryId,
            technologyId,
            order,
            orderBy,
            authorId
        );

        List<ChallengeDTOView> challenges = response.getItems()
            .stream()
            .map(ChallengeDTOView::new)
            .collect(Collectors.toList());

        return new ItemsWithPagination<List<ChallengeDTOView>>(
            challenges,
            response.getPagination()
        );

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

    public List<ChallengeDTOView> findUserChallenges(UUID userId, Integer page, Integer size) {
        return challengeRepository.findUserChallenges(userId, page, size)
            .stream()
            .map(ChallengeDTOView::new)
            .toList();
    }

    public List<UserDTOView> findAllUsersForChallenge(UUID challengeId, Integer page, Integer size) {
        return userRepository.findAllUsersForChallenge(challengeId, page, size)
            .stream()
            .map(UserDTOView::new)
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

        Image image = imageService.saveImage(challengeDTOForm.getImage());
        challenge.setImage(image);

        challenge = this.challengeRepository.createChallenge(challenge);

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
    public boolean joinChallenge(UUID challengeId, UUID participantId) throws JoinNotAcceptedException, SQLException {
        return challengeRepository.joinChallenge(challengeId, participantId);
    }

    @Transactional
    public boolean unjoinChallenge(UUID challengeId, UUID participantId) throws UnjoinNotAcceptedException, SQLException {
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
