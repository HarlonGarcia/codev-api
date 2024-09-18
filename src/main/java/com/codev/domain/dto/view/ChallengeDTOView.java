package com.codev.domain.dto.view;

import com.codev.domain.enums.ChallengeStatus;
import com.codev.domain.model.Challenge;
import com.codev.domain.model.Image;
import com.codev.utils.helpers.DtoTransformer;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
public class ChallengeDTOView {

    private UUID id;

    private String title;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime endDate;

    private ChallengeStatus status;

    private UserDTOView author;

    private CategoryDTOView category;

    private Set<TechnologyDTOView> technologies;

    private List<Image> images;

    public ChallengeDTOView(Challenge challenge) {
        DtoTransformer<Challenge, ChallengeDTOView> transformChallenge = new DtoTransformer<>();
        transformChallenge.copyProperties(challenge, this);
    }
    
    public ChallengeDTOView() {}

}
