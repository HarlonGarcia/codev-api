package com.codev.domain.dto.view;

import com.codev.domain.model.User;
import lombok.Data;

@Data
public class UserDTOView {
    private Long id;

    private String name;

    private String email;

    private String password;

    public UserDTOView(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }

    public UserDTOView() {}
}
