package com.codev.domain.dto.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TechnologyDTOForm {

    @NotBlank
    private String name;

    @NotBlank
    private String documentationLink;

    @NotBlank
    private String color;

}
