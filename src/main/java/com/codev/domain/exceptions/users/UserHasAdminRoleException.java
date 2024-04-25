package com.codev.domain.exceptions.users;

import com.codev.domain.exceptions.global.ExceptionResponse;
import jakarta.ws.rs.core.Response;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class UserHasAdminRoleException extends Exception {

    private ExceptionResponse exceptionResponse;

    public UserHasAdminRoleException() {
        super("User already has admin role.");

        this.exceptionResponse =
            new ExceptionResponse(
                Response.Status.NOT_ACCEPTABLE.getStatusCode(),
                "User already has admin role.",
                ""
            );
    }
}
