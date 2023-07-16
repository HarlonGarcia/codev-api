package com.codev.domain.dto.view;

import lombok.Data;

@Data
public class SolutionDTOView {

    private Long challengeId;

    private Long authorId;

    private String repositoryUrl;

    private String deployUrl;

    private long likes;

    public SolutionDTOView(Long challengeId, Long authorId, String repositoryUrl, String deployUrl, long likes) {
        this.challengeId = challengeId;
        this.authorId = authorId;
        this.repositoryUrl = repositoryUrl;
        this.deployUrl = deployUrl;
        this.likes = likes;
    }

}
