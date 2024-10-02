package com.codev.domain.service;

import com.codev.domain.dto.view.MeDTOView;
import com.codev.domain.exceptions.token.ExtractEmailFromTokenException;
import com.codev.domain.exceptions.users.UserDeactivatedException;
import com.codev.domain.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ApplicationScoped
@RequiredArgsConstructor
public class MeService {

    private final UserService userService;

    public MeDTOView findMe(String token) throws ExtractEmailFromTokenException, UserDeactivatedException {

        String email = extractEmail(token);

        if (!email.isEmpty()) {
            User user = userService.findByEmail(email);
            return new MeDTOView(user);
        }

        throw new ExtractEmailFromTokenException();
    }

    private static String extractEmail(String token) {

        String[] chunks = token.split("\\.");

        Base64.Decoder decoder = Base64.getUrlDecoder();

        String payload = new String(decoder.decode(chunks[1]));

        String emailRegex = "\"sub\":\"([^\"]+)\"";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(payload);

        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return "";
        }
    }

}
