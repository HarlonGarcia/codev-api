package com.codev.domain.exceptions.users;

import com.codev.domain.exceptions.global.ExceptionResponse;
import jakarta.ws.rs.core.Response;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class InvalidRefreshTokenException extends Exception {

    private ExceptionResponse exceptionResponse;

    public InvalidRefreshTokenException() {
        super("Bad request to refresh token.");

        this.exceptionResponse =
            new ExceptionResponse(
                Response.Status.UNAUTHORIZED.getStatusCode(),
                "Bad request to refresh token.",
                ""
            );
    }

}
