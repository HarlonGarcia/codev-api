package com.codev.domain.dto.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TechnologyDTOForm {

    @NotBlank
    private String name;

    @NotBlank
    private String documentationLink;

    @NotBlank
    @Size(min = 6, max = 6, message = "Color must be a valid 6-digit hexadecimal color.")
    private String color;

}
