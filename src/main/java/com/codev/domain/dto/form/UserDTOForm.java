package com.codev.domain.dto.form;

import com.codev.domain.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Set;

@Data
public class UserDTOForm {

    @NotBlank
    private String name;

    @Email
    private String email;

    @NotBlank
    private String password;

    private String githubUrl;

    private String additionalUrl;

    private Set<Role> roles;

}
