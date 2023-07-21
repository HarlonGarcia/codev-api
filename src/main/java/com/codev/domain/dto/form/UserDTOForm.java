package com.codev.domain.dto.form;

import com.codev.domain.model.Role;
import lombok.Data;

import java.util.Set;

@Data
public class UserDTOForm {

    private String name;

    private String email;

    private String password;

    private String githubUrl;

    private String additionalUrl;

    private Set<Role> roles;

}
