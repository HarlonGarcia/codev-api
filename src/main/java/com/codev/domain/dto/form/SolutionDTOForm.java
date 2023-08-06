package com.codev.domain.dto.form;

import lombok.Data;

import java.util.UUID;

@Data
public class SolutionDTOForm {

    private UUID challengeId;

    private UUID authorId;

    private String repositoryUrl;

    private String deployUrl;
}
