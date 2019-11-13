package net.skai.chefplaneteapi.domain.response;

import org.jetbrains.annotations.NotNull;

public class AuthResponse {

    private final String token;

    public AuthResponse(@NotNull final String token) {
        this.token = token;
    }

    @NotNull
    public String getToken() {
        return token;
    }
}
