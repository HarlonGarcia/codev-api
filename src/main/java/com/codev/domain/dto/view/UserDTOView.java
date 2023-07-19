package com.codev.domain.dto.view;

import com.codev.domain.model.User;
import lombok.Data;

@Data
public class UserDTOView {
    private Long id;

    private String name;

    private String email;

    private String password;

    private String githubUrl;

    private String additionalUrl;

    public UserDTOView(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.additionalUrl = user.getAdditionalUrl();
        this.githubUrl = user.getGithubUrl();
    }

    public UserDTOView() {}
}
