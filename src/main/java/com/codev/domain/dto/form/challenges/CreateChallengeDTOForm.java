package com.codev.domain.dto.form.challenges;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;
import java.util.UUID;

import com.codev.domain.enums.ChallengeStatus;

@Data
public class CreateChallengeDTOForm {

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
    private List<UUID> technologies;

    @NotNull
    private ChallengeStatus status;
}
