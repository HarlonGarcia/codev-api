package com.codev.domain.dto.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryDTOForm {

    @NotBlank
    private String name;

}
