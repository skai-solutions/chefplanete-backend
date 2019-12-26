package net.skai.chefplaneteapi.exception;

import org.jetbrains.annotations.NotNull;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(@NotNull String userId) {
        super("The user with id: " + userId + " could not be found.");
    }
}
