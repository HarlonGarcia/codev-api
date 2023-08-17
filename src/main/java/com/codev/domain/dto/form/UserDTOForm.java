package com.codev.domain.dto.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

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

}
