package com.codev.domain.dto.view;

import com.codev.domain.model.Label;
import com.codev.domain.model.Role;
import com.codev.domain.model.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class UserDTOView {

    private UUID id;

    private String name;

    private String email;

    private String githubUrl;

    private String additionalUrl;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Set<Label> labels;

    private Set<Role> roles;

    public UserDTOView(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.additionalUrl = user.getAdditionalUrl();
        this.githubUrl = user.getGithubUrl();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
        this.labels = user.getLabels();
        this.roles = new HashSet<>(user.getRoles());
    }

    public UserDTOView() {}
}
