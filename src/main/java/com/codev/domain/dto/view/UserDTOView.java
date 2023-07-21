package com.codev.domain.dto.view;

import com.codev.domain.model.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTOView {
    private Long id;

    private String name;

    private String email;

    private String githubUrl;

    private String additionalUrl;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public UserDTOView(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.additionalUrl = user.getAdditionalUrl();
        this.githubUrl = user.getGithubUrl();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = LocalDateTime.now();
    }

    public UserDTOView() {}
}
