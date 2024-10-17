package com.codev.domain.dto.form;

import java.util.UUID;

import lombok.Data;

@Data
public class ImageDTOForm {

    private UUID id;

    private String fileName;

    private String file;

}
