package net.skai.chefplaneteapi.exception;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;

public class JwtTokenException extends AuthenticationException {

    @NotNull
    public JwtTokenException(@NotNull final String message, @NotNull final HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
