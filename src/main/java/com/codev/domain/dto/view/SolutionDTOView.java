package com.codev.domain.dto.view;

import lombok.Data;

@Data
public class SolutionDTOView {

    private Long challengeId;

    private Long authorId;

    private String repositoryUrl;

    private String deployUrl;

    public SolutionDTOView(Long challengeId, Long authorId, String repositoryUrl, String deployUrl) {
        this.challengeId = challengeId;
        this.authorId = authorId;
        this.repositoryUrl = repositoryUrl;
        this.deployUrl = deployUrl;
    }

}
