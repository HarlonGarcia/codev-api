package com.codev.domain.dto.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDTOForm {

    @NotBlank
    private String name;

    @Email
    private String email;

    @NotBlank
    @Size(min = 8, message = "Password must be a valid 8-digit.")
    private String password;

    private String githubUrl;

    private String additionalUrl;

}
