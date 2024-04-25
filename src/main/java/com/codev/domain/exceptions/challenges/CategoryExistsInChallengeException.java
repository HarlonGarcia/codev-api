package com.codev.domain.exceptions.challenges;

import com.codev.domain.exceptions.global.ExceptionResponse;
import jakarta.ws.rs.core.Response;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CategoryExistsInChallengeException extends RuntimeException {

    private ExceptionResponse exceptionResponse;

    public CategoryExistsInChallengeException() {
        super("Category already exists in challenge");

        this.exceptionResponse =
            new ExceptionResponse(
                Response.Status.BAD_REQUEST.getStatusCode(),
                "Category already exists in challenge",
                ""
            );
    }

}
