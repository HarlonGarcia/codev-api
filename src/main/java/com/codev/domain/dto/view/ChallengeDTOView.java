package com.codev.domain.dto.view;

import com.codev.domain.model.Challenge;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChallengeDTOView {

    private Long id;

    private String title;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime endDate;

    public ChallengeDTOView(Challenge challenge) {
        this.id = challenge.getId();
        this.title = challenge.getTitle();
        this.description = challenge.getDescription();
        this.createdAt = challenge.getCreatedAt();
        this.endDate = challenge.getEndDate();
    }

}
