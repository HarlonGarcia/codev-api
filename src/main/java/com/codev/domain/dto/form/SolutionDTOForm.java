package com.codev.domain.dto.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.UUID;

@Data
public class SolutionDTOForm {

    @NotNull
    private UUID challengeId;

    @NotNull
    private UUID authorId;

    @NotBlank
    private String repositoryUrl;

    private String deployUrl;

    private ImageDTOForm image;

}
