package com.codev.domain.exceptions.global;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ErrorResponse {

    private int status;
    private String type;
    private List<Violation> violations;

    public ErrorResponse() {
    }

    public ErrorResponse(int status, String type, List<Violation> violations) {
        this.status = status;
        this.type = type;
        this.violations = violations;
    }

    public ErrorResponse(int status, String type, Violation violation) {
        this.status = status;
        this.type = type;
        this.violations = new ArrayList<>();
        addViolation(violation);
    }

    public void addViolation(Violation violation) {
        this.violations.add(violation);
    }

}
