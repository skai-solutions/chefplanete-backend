package net.skai.chefplaneteapi.service.auth.exception;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;

public class AuthenticationException extends RuntimeException {

    private final HttpStatus httpStatus;

    public AuthenticationException(@NotNull final String message, @NotNull final HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
