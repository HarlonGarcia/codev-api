package com.codev.domain.dto.view;

import com.codev.domain.enums.ChallengeStatus;
import com.codev.domain.model.Category;
import com.codev.domain.model.Challenge;
import lombok.Data;

import java.time.LocalDateTime;
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

    //todo: retornar technologies

    public ChallengeDTOView(Challenge challenge) {
        this.id = challenge.getId();
        this.title = challenge.getTitle();
        this.description = challenge.getDescription();
        this.createdAt = challenge.getCreatedAt();
        this.endDate = challenge.getEndDate();
        this.status = challenge.getStatus();
        this.author = new UserDTOView(challenge.getAuthor());    
    }
    
    public ChallengeDTOView(Challenge challenge, Category category) {
        this(challenge);
        this.category = new CategoryDTOView(challenge.getCategory());
    }

}
