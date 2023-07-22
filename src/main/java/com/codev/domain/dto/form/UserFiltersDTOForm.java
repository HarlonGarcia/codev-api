package com.codev.domain.dto.form;

import lombok.Data;

@Data
public class UserFiltersDTOForm {
    String startsWith;

    public UserFiltersDTOForm(String startsWith) {
        this.startsWith = startsWith;
    }
}
