package com.codev.domain.dto.view;

import lombok.Data;

@Data
public class SolutionDTOView {

    private Long challengeId;

    private Long authorId;

    private String repositoryUrl;

    private String deployUrl;

}
