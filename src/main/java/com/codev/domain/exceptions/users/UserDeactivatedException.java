package com.codev.domain.exceptions.users;

import com.codev.domain.exceptions.global.ExceptionResponse;
import jakarta.ws.rs.core.Response;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class UserDeactivatedException extends Exception {

    private ExceptionResponse exceptionResponse;

    public UserDeactivatedException() {
        super("User is deactivated.");

        this.exceptionResponse =
            new ExceptionResponse(
                Response.Status.NOT_ACCEPTABLE.getStatusCode(),
                "User is deactivated.",
                ""
            );
    }
}
