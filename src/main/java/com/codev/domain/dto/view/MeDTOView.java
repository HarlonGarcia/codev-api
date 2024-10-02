package com.codev.domain.dto.view;

import com.codev.domain.model.Image;
import com.codev.domain.model.Label;
import com.codev.domain.model.Role;
import com.codev.domain.model.User;
import com.codev.utils.helpers.DtoTransformer;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
public class MeDTOView {

    private UUID id;

    private String name;

    private String email;

    private Image image;

    private String githubUrl;

    private String additionalUrl;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Set<Label> labels;

    private Set<Role> roles;

    private boolean following;

    public MeDTOView(User user) {
        DtoTransformer<User, MeDTOView> transform = new DtoTransformer<>();
        transform.copyProperties(user, this);
    }

    public MeDTOView(UUID id, String name, String email, String githubUrl, String additionalUrl,
                       LocalDateTime createdAt, LocalDateTime updatedAt, boolean following
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.githubUrl = githubUrl;
        this.additionalUrl = additionalUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.following = following;
    }

    public MeDTOView() {}
}