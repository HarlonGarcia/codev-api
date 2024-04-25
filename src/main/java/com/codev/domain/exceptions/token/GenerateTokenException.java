package com.codev.domain.exceptions.token;

import com.codev.domain.exceptions.global.ExceptionResponse;
import jakarta.ws.rs.core.Response;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class GenerateTokenException extends RuntimeException {

    private ExceptionResponse exceptionResponse;

    public GenerateTokenException() {
        super("Token generation failed.");

        this.exceptionResponse =
            new ExceptionResponse(
                Response.Status.BAD_REQUEST.getStatusCode(),
                "Token generation failed.",
                ""
            );
    }
}
