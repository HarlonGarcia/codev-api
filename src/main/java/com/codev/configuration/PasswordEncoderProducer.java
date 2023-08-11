package com.codev.configuration;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ApplicationScoped
public class PasswordEncoderProducer {

    @Produces
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
