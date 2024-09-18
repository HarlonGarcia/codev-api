package com.codev.domain.dto.view;

import com.codev.domain.model.Label;
import com.codev.domain.model.Role;
import com.codev.domain.model.User;
import com.codev.utils.helpers.DtoTransformer;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
public class UserDTOView {

    private UUID id;

    private String name;

    private String email;

    private String image;

    private String githubUrl;

    private String additionalUrl;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Set<Label> labels;

    private Set<Role> roles;

    private boolean following;

    public UserDTOView(User user) {
        DtoTransformer<User, UserDTOView> transform = new DtoTransformer<>();
        transform.copyProperties(user, this);
    }

    public UserDTOView(UUID id, String name, String email, String githubUrl, String additionalUrl,
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

    public UserDTOView() {}
}
