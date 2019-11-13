package net.skai.chefplaneteapi.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import net.skai.chefplaneteapi.domain.Role;
import net.skai.chefplaneteapi.domain.response.AuthResponse;
import net.skai.chefplaneteapi.service.auth.JwtTokenProvider;
import net.skai.chefplaneteapi.service.auth.exception.AuthenticationException;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    private final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final JwtTokenProvider tokenProvider;

    private final List<String> inMemoryUserIds;

    private final GoogleIdTokenVerifier idTokenVerifier;

    public AuthServiceImpl(@NotNull final JwtTokenProvider tokenProvider,
                           @NotNull final GoogleIdTokenVerifier idTokenVerifier) {
        this.tokenProvider = tokenProvider;
        this.inMemoryUserIds = new ArrayList<>();
        this.idTokenVerifier = idTokenVerifier;
    }

    @Override
    public boolean userExists(@NotNull final String userId) {
        return inMemoryUserIds.contains(userId);
    }

    @Override
    public @NotNull AuthResponse login(@NotNull final String idToken) {
        final GoogleIdToken googleIdToken = verifyGoogleId(idToken);
        final GoogleIdToken.Payload payload = googleIdToken.getPayload();
        if (inMemoryUserIds.contains(payload.getSubject())) {
            final String token = tokenProvider.createToken(
                    payload.getSubject(),
                    Collections.singletonList(Role.ROLE_CLIENT));
            return new AuthResponse(token);
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
        if (!userExists(payload.getSubject())) {
            final String token = tokenProvider.createToken(
                    payload.getSubject(),
                    Collections.singletonList(Role.ROLE_CLIENT));
            inMemoryUserIds.add(payload.getSubject());
            return new AuthResponse(token);
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

    @NotNull
    public List<String> getInMemoryUserIds() {
        return inMemoryUserIds;
    }
}
