package net.skai.chefplaneteapi.exception;

import org.jetbrains.annotations.NotNull;

public class UserExistsException extends RuntimeException {
    public UserExistsException(@NotNull String userId) {
        super("The user with id: " + userId + " already exists.");
    }
}
