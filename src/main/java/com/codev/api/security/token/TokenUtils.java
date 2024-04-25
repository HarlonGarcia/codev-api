package com.codev.api.security.token;

import com.codev.domain.dto.view.UserDTOView;
import com.codev.domain.exceptions.token.GenerateTokenException;
import com.codev.domain.model.Role;
import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ApplicationScoped
public class TokenUtils {

    public static final long TOKEN_DURATION = 86400;

    public String getAccessToken(String authorizationHeader) {
        if (authorizationHeader != null) {
            if (authorizationHeader.startsWith("Bearer ")) {
                String accessToken = authorizationHeader.substring("Bearer ".length()).trim();
                return accessToken;
            } else {
                throw new IllegalArgumentException("The access token is not of type Bearer");
            }
        } else {
            throw new IllegalArgumentException("Header authorization is null");
        }
    }

    public static String generateToken(String username, List<Role> roles) {
        try {
            String privateKeyLocation = "/privatekey.pem";
            PrivateKey privateKey = readPrivateKey(privateKeyLocation);

            JwtClaimsBuilder claimsBuilder = Jwt.claims();
            long currentTimeInSecs = currentTimeInSecs();

            Set<String> groups = new HashSet<>();
            for (Role role : roles) groups.add(role.getName());

            claimsBuilder.subject(username);
            claimsBuilder.issuedAt(currentTimeInSecs);
            claimsBuilder.expiresAt(currentTimeInSecs + TOKEN_DURATION);
            claimsBuilder.groups(groups);

            return claimsBuilder.jws().keyId(privateKeyLocation).sign(privateKey);

        } catch (Exception e) {
            throw new GenerateTokenException();
        }
    }

    public static PrivateKey readPrivateKey(final String pemResName) throws Exception {
        try (InputStream contentIS = TokenUtils.class.getResourceAsStream(pemResName)) {
            byte[] tmp = new byte[4096];
            int length = contentIS.read(tmp);
            return decodePrivateKey(new String(tmp, 0, length, "UTF-8"));
        }
    }

    public static PrivateKey decodePrivateKey(final String pemEncoded) throws Exception {
        byte[] encodedBytes = toEncodedBytes(pemEncoded);

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encodedBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(keySpec);
    }

    public static byte[] toEncodedBytes(final String pemEncoded) {
        final String normalizedPem = removeBeginEnd(pemEncoded);
        return Base64.getDecoder().decode(normalizedPem);
    }

    public static String removeBeginEnd(String pem) {
        pem = pem.replaceAll("-----BEGIN (.*)-----", "");
        pem = pem.replaceAll("-----END (.*)----", "");
        pem = pem.replaceAll("\r\n", "");
        pem = pem.replaceAll("\n", "");
        return pem.trim();
    }

    public static int currentTimeInSecs() {
        long currentTimeMS = System.currentTimeMillis();
        return (int) (currentTimeMS / 1000);
    }

}