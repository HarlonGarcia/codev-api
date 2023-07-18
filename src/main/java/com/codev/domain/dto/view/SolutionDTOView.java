package com.codev.domain.dto.view;

import com.codev.domain.model.Solution;
import com.codev.domain.model.User;
import lombok.Data;

@Data
public class SolutionDTOView {

    private Long challengeId;

    private UserDTOView author;

    private String repositoryUrl;

    private String deployUrl;

    private long likes;

    private boolean liked;

    public SolutionDTOView(Long challengeId, User author, String repositoryUrl, String deployUrl, long likes, boolean liked) {
        this.challengeId = challengeId;
        this.author = new UserDTOView(author);
        this.repositoryUrl = repositoryUrl;
        this.deployUrl = deployUrl;
        this.likes = likes;
        this.liked = liked;
    }

    public SolutionDTOView(Solution solution) {
        this.challengeId = solution.getChallenge().getId();
        this.author = new UserDTOView(solution.getAuthor());
        this.repositoryUrl = solution.getRepositoryUrl();
        this.deployUrl = solution.getDeployUrl();
        this.likes = 0;
    }

}
