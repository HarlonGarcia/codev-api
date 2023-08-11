package com.codev.domain.dto.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

import com.codev.domain.enums.ChallengeStatus;

@Data
public class ChallengeDTOForm {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotNull
    private UUID authorId;

    @NotBlank
    private String imageURL;

    @NotNull
    private UUID categoryId;

    @NotNull
    private ChallengeStatus status;

}
