package com.codev.domain.dto.form.challenges;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;
import java.util.UUID;

import com.codev.domain.enums.ChallengeStatus;
import com.codev.domain.model.Technology;

@Data
public class ChallengeDTOForm {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotNull
    private UUID authorId;

    @NotBlank
    private String imageUrl;

    @NotNull
    private UUID categoryId;

    @NotNull
    private List<Technology> technologies;

    @NotNull
    private ChallengeStatus status;

    public ChallengeDTOForm(CreateChallengeDTOForm challenge) {
        this.title = challenge.getTitle();
        this.description = challenge.getDescription();
        this.authorId = challenge.getAuthorId();
        this.imageUrl = challenge.getImageUrl();
        this.categoryId = challenge.getCategoryId();
        this.status = challenge.getStatus();
    }
}
