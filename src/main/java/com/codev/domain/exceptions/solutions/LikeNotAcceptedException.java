package com.codev.domain.exceptions.solutions;

import com.codev.domain.exceptions.global.ExceptionResponse;
import jakarta.ws.rs.core.Response;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class LikeNotAcceptedException extends Exception {

    private ExceptionResponse exceptionResponse;

    public LikeNotAcceptedException() {
        super("Like not accepted: The user has already given a like.");

        this.exceptionResponse =
            new ExceptionResponse(
                Response.Status.NOT_ACCEPTABLE.getStatusCode(),
                "Like not accepted: The user has already given a like.",
                ""
            );
    }
}
