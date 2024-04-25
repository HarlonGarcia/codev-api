package com.codev.domain.exceptions.solutions;

import com.codev.domain.exceptions.global.ExceptionResponse;
import jakarta.ws.rs.core.Response;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class SolutionNotDeletedException extends Exception {

    private ExceptionResponse exceptionResponse;

    public SolutionNotDeletedException(String message) {
        super("Solution not deleted: " + message);

        this.exceptionResponse =
            new ExceptionResponse(
                Response.Status.BAD_REQUEST.getStatusCode(),
                "Solution not deleted:" + message,
                ""
            );
    }
}
