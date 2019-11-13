package net.skai.chefplaneteapi.service;

import net.skai.chefplaneteapi.domain.User;
import net.skai.chefplaneteapi.domain.response.AuthResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AuthService {

    @NotNull
    boolean userExists(@NotNull final String userId);

    @NotNull
    AuthResponse login(@NotNull final String idToken);

    @NotNull
    AuthResponse signup(@NotNull final String idToken);
}
