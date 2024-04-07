package com.codev.domain.exceptions.global;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ErrorResponse {

    private int status;
    private String title;
    private List<Violation> violations;

    public ErrorResponse() {
    }

    public ErrorResponse(int status, String title, List<Violation> violations) {
        this.status = status;
        this.title = title;
        this.violations = violations;
    }

    public ErrorResponse(int status, String title, Violation violation) {
        this.status = status;
        this.title = title;
        this.violations = new ArrayList<>();
        addViolation(violation);
    }

    public void addViolation(Violation violation) {
        this.violations.add(violation);
    }

}
