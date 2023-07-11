package com.codev.domain.dto.view;

import com.codev.domain.model.Challenge;
import lombok.Data;

@Data
public class ChallengeDTOView {

    private Long id;

    private String title;

    private String description;

    private long like;

    public ChallengeDTOView(Challenge challenge) {
        this.id = challenge.getId();
        this.title = challenge.getTitle();
        this.description = challenge.getDescription();
        this.like = 0;
    }

    public ChallengeDTOView(Long id, String title, String description, long like) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.like = like;
    }

}
