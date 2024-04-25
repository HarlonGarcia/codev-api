package com.codev.domain.exceptions.users;

import com.codev.domain.exceptions.global.ExceptionResponse;
import jakarta.ws.rs.core.Response;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class UserIsAlreadyBeingFollowedResponse {

    private ExceptionResponse exceptionResponse;

    public UserIsAlreadyBeingFollowedResponse() {
        this.exceptionResponse =
            new ExceptionResponse(
                Response.Status.BAD_REQUEST.getStatusCode(),
                "User is already being followed.",
                ""
            );
    }
}
