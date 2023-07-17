package com.codev.domain.dto.form;

import lombok.Data;

@Data
public class SolutionDTOForm {

    private Long challengeId;

    private Long authorId;

    private String repositoryUrl;

    private String deployUrl;
}
