package com.codev.domain.dto.form;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class SolutionDTOForm {

    private Long challengeId;

    private Long authorId;

    private String repositoryUrl;

    private String deployUrl;
}
