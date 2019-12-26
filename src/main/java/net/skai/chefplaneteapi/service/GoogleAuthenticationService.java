package net.skai.chefplaneteapi.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import net.skai.chefplaneteapi.domain.Role;
import net.skai.chefplaneteapi.domain.User;
import net.skai.chefplaneteapi.domain.response.AuthResponse;
import net.skai.chefplaneteapi.exception.AuthenticationException;
import net.skai.chefplaneteapi.service.auth.JwtTokenProvider;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class GoogleAuthenticationService implements AuthService {

    private final Logger LOGGER = LoggerFactory.getLogger(GoogleAuthenticationService.class);

    private final JwtTokenProvider tokenProvider;

    private final GoogleIdTokenVerifier idTokenVerifier;

    private final UserService userService;

    public GoogleAuthenticationService(@NotNull final JwtTokenProvider tokenProvider,
                                       @NotNull final GoogleIdTokenVerifier idTokenVerifier,
                                       @NotNull final UserService userService) {
        this.tokenProvider = tokenProvider;
        this.idTokenVerifier = idTokenVerifier;
        this.userService = userService;
    }

    @Override
    public @NotNull AuthResponse login(@NotNull final String idToken) {
        final GoogleIdToken googleIdToken = verifyGoogleId(idToken);
        final GoogleIdToken.Payload payload = googleIdToken.getPayload();
        if (userService.userExists(payload.getSubject())) {
            final String token = tokenProvider.createToken(
                    payload.getSubject(),
                    Collections.singletonList(Role.ROLE_CLIENT));
            return new AuthResponse(token, payload.getSubject());
        } else {
            LOGGER.warn("User with token: [" + idToken + "] is not registered.");
            throw new AuthenticationException("This google account does not exist in our records.", HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    @NotNull
    public AuthResponse signup(@NotNull final String idToken) {
        final GoogleIdToken googleIdToken = verifyGoogleId(idToken);
        final GoogleIdToken.Payload payload = googleIdToken.getPayload();
        if (!userService.userExists(payload.getSubject())) {
            final String token = tokenProvider.createToken(
                    payload.getSubject(),
                    Collections.singletonList(Role.ROLE_CLIENT));
            final User newUser = new User(payload.getSubject(), payload.getEmail(), (String) payload.get("name"));
            userService.addUser(newUser);
            return new AuthResponse(token, payload.getSubject());
        } else {
            throw new AuthenticationException("This google account is already associated.", HttpStatus.CONFLICT);
        }
    }

    @NotNull
    private GoogleIdToken verifyGoogleId(@NotNull final String idToken) {
        GoogleIdToken googleIdToken;
        try {
            googleIdToken = idTokenVerifier.verify(idToken);
        } catch (Exception e) {
            throw new AuthenticationException("Error attempting to validate.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (googleIdToken == null) {
            throw new AuthenticationException("The id token provided is invalid.", HttpStatus.BAD_REQUEST);
        }
        return googleIdToken;
    }
}
