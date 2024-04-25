package com.codev.domain.exceptions.challenges;

import com.codev.domain.exceptions.global.ExceptionResponse;
import jakarta.ws.rs.core.Response;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class JoinNotAcceptedException extends RuntimeException {

    private ExceptionResponse exceptionResponse;

    public JoinNotAcceptedException() {
        super("Join not accepted: The user has already given a join");

        this.exceptionResponse =
            new ExceptionResponse(
                Response.Status.BAD_REQUEST.getStatusCode(),
                "Join not accepted: The user has already given a join.",
                ""
            );
    }
}