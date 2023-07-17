package com.codev.domain.dto.view;

import com.codev.domain.model.Solution;
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

    public SolutionDTOView(Solution solution) {
        this.challengeId = solution.getChallenge().getId();
        this.authorId = solution.getAuthor().getId();
        this.repositoryUrl = solution.getRepositoryUrl();
        this.deployUrl = solution.getDeployUrl();
        this.likes = 0;
    }

}
