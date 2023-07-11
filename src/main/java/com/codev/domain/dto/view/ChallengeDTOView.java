package com.codev.domain.dto.view;

import com.codev.domain.model.Challenge;
import lombok.Data;

@Data
public class ChallengeDTOView {

    private Long id;

    private String title;

    private String description;

    public ChallengeDTOView(Challenge challenge) {
        this.id = challenge.getId();
        this.title = challenge.getTitle();
        this.description = challenge.getDescription();
    }
}
