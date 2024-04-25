package com.codev.domain.exceptions.users;

import com.codev.domain.exceptions.global.ExceptionResponse;
import jakarta.ws.rs.core.Response;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class InvalidLoginException extends Exception {

    private ExceptionResponse exceptionResponse;

    public InvalidLoginException() {
        super("Email or password is incorrect.");

        this.exceptionResponse =
            new ExceptionResponse(
                Response.Status.UNAUTHORIZED.getStatusCode(),
                "Email or password is incorrect.",
                ""
            );
    }

}
