package com.codev.domain.dto.form;

import lombok.Data;

import java.util.UUID;

@Data
public class ChallengeDTOForm {

    private String title;

    private String description;

    private UUID authorId;

    private String imageURL;

    private UUID categoryId;

}
