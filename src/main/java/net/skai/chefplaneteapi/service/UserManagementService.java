package net.skai.chefplaneteapi.service;

import net.skai.chefplaneteapi.domain.User;
import net.skai.chefplaneteapi.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

@Service
public class UserManagementService implements UserService {

    private final UserRepository userRepository;

    public UserManagementService(@NotNull final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public @Nullable User getUserById(@NotNull final String userId) {
        if (userRepository.existsById(userId)) {
            return userRepository.findUserByUserId(userId);
        }
        return null;
    }

    @Override
    public boolean deleteUserById(@NotNull final String userId) {
        if (userRepository.existsById(userId)) {
            userRepository.removeUserByUserId(userId);
            return true;
        }
        return false;
    }

    @Override
    public boolean addUser(@NotNull final User user) {
        if (!userRepository.existsById(user.getUserId())) {
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean userExists(@NotNull final String userId) {
        return userRepository.existsById(userId);
    }
}
