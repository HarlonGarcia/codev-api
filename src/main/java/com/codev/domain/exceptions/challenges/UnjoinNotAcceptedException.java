package com.codev.domain.exceptions.challenges;

import com.codev.domain.exceptions.global.ExceptionResponse;
import jakarta.ws.rs.core.Response;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UnjoinNotAcceptedException extends RuntimeException {

    private ExceptionResponse exceptionResponse;

    public UnjoinNotAcceptedException() {
        super("Unjoin not accepted: The user has already unjoined the challenge");

        this.exceptionResponse =
            new ExceptionResponse(
                Response.Status.BAD_REQUEST.getStatusCode(),
                "Unjoin not accepted: The user has already unjoined the challenge",
                ""
            );
    }
}
