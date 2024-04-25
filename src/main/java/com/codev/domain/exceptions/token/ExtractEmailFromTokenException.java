package com.codev.domain.exceptions.token;

import com.codev.domain.exceptions.global.ExceptionResponse;
import jakarta.ws.rs.core.Response;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class ExtractEmailFromTokenException extends Exception {

    private ExceptionResponse exceptionResponse;

    public ExtractEmailFromTokenException() {
        super("Incorrect token or the token's 'subject' does not represent an email address.");

        this.exceptionResponse =
            new ExceptionResponse(
                Response.Status.BAD_REQUEST.getStatusCode(),
                "Incorrect token or the token's 'subject' does not represent an email address.",
                ""
            );
    }

}
