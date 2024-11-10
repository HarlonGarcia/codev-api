package com.codev.domain.dto.form;

import com.codev.domain.enums.ChallengeStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class ChallengeDTOForm {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotNull
    private UUID authorId;

    private ImageDTOForm image;

    @NotNull
    private UUID categoryId;

    private Set<UUID> technologies;

    @NotNull
    private ChallengeStatus status;

}
