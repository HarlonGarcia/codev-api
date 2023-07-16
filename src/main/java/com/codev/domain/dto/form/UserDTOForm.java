package com.codev.domain.dto.form;

import com.codev.domain.model.Role;
import lombok.Data;

import java.util.Set;

@Data
public class UserDTOForm {

    private Long id;

    private String name;

    private String email;

    private Set<Role> roles;
}
