package com.codev.domain.dto.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class TechnologyDTOForm {

    @NotNull
    private UUID id;

    @NotBlank
    private String name;

    @NotBlank
    private String documentationLink;

    @NotBlank
    @Size(min = 6, max = 6, message = "Color must be a valid 6-digit hexadecimal color.")
    private String color;

}
