package com.codev.domain.exceptions.users;

import com.codev.domain.exceptions.global.ExceptionResponse;
import jakarta.ws.rs.core.Response;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class UserHasAlreadyUnfollowedResponse {

    private ExceptionResponse exceptionResponse;

    public UserHasAlreadyUnfollowedResponse() {
        this.exceptionResponse =
            new ExceptionResponse(
                Response.Status.BAD_REQUEST.getStatusCode(),
                "User has already unfollowed.",
                ""
            );
    }

}
