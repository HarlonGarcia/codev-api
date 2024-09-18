package com.codev.domain.dto.form;

import com.codev.domain.enums.ChallengeStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ChallengeDTOForm {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotNull
    private UUID authorId;

    private List<ImageDTOForm> images;

    @NotNull
    private UUID categoryId;

    @NotNull
    private ChallengeStatus status;

}
