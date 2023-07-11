<<<<<<< HEAD
package com.codev.api.resource;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

@Liveness
@ApplicationScoped
public class HealthCheckResource implements HealthCheck {

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
=======
package com.codev.api.resource;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

@Liveness
@ApplicationScoped
public class HealthCheckResource implements HealthCheck {

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
>>>>>>> 0621218cc0a887b73c4ea36637e1b94362cec9f6
}