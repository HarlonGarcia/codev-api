package com.codev.domain.dto.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserUpdateDTOForm {
    private String name;

    @Email
    private String email;

    @Size(min = 8, message = "Password must be a valid 8-digit.")
    private String password;

    private String githubUrl;

    private String additionalUrl;

    private ImageDTOForm image;

    public UserUpdateDTOForm() {}

}
