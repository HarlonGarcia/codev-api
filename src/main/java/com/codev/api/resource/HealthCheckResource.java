package com.codev.api.controller;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

@Liveness
@ApplicationScoped
public class HealthCheckController implements HealthCheck {

    @Override
    public HealthCheckResponse call() {
        boolean isDatabaseUp = checkDatabaseStatus();

        if (isDatabaseUp) {
            return HealthCheckResponse.up("Database is up");
        }

        return HealthCheckResponse.down("Database is down");
    }

    private boolean checkDatabaseStatus() {
        // toBeImplemented
        return false;
    }
}