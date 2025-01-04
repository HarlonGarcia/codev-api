package com.codev.domain.dto.view;

import com.codev.domain.model.Image;
import com.codev.domain.model.Solution;
import com.codev.domain.model.User;
import com.codev.utils.helpers.DtoTransformer;
import lombok.Data;

import java.util.UUID;

@Data
public class SolutionDTOView {

    private UUID solutionId;

    private UUID challengeId;

    private UserDTOView author;

    private String repositoryUrl;

    private String deployUrl;

    private long likes;

    private Image image;

    private boolean liked;

    public SolutionDTOView(UUID challengeId, User author, String repositoryUrl, String deployUrl, long likes, boolean liked) {
        this.challengeId = challengeId;
        this.author = new UserDTOView(author);
        this.repositoryUrl = repositoryUrl;
        this.deployUrl = deployUrl;
        this.likes = likes;
        this.liked = liked;
        this.image = new Image();
    }

    public SolutionDTOView(Solution solution) {
        DtoTransformer<Solution, SolutionDTOView> transform = new DtoTransformer<>();
        transform.copyProperties(solution, this);
    }

    public SolutionDTOView(){}

}
