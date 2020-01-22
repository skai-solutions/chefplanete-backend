package net.skai.chefplaneteapi.service;

import net.skai.chefplaneteapi.domain.response.AuthResponse;
import org.jetbrains.annotations.NotNull;

public interface AuthService {

    @NotNull
    AuthResponse login(@NotNull final String idToken);
}
