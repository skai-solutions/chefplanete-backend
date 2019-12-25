package net.skai.chefplaneteapi.domain.response;

import org.jetbrains.annotations.NotNull;

public class AuthResponse {

    private final String userId;

    private final String token;

    public AuthResponse(@NotNull final String token,
                        @NotNull final String userId) {
        this.token = token;
        this.userId = userId;
    }

    @NotNull
    public String getUserId() {
        return userId;
    }

    @NotNull
    public String getToken() {
        return token;
    }
}
