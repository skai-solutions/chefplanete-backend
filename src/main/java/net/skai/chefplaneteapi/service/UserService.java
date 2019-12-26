package net.skai.chefplaneteapi.service;

import net.skai.chefplaneteapi.domain.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface UserService {

    @Nullable
    User getUserById(@NotNull final String userId);

    boolean deleteUserById(@NotNull final String userId);

    boolean addUser(@NotNull final User user);

    boolean userExists(@NotNull final String userId);
}
