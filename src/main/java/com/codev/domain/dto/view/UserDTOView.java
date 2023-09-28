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

    private String githubUrl;

    private String additionalUrl;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Set<Label> labels;

    private Set<Role> roles;

    public UserDTOView(User user) {
        DtoTransformer<User, UserDTOView> transform = new DtoTransformer<>();
        transform.copyProperties(user, this);
    }

    public UserDTOView() {}
}
