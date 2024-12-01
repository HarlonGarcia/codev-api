package com.codev.api.security.auth;

import jakarta.ws.rs.core.NewCookie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class AuthResponse {

    public String token;
    public NewCookie refreshCookie;

    public AuthResponse(String token) {
        this.token = token;
    }
}
