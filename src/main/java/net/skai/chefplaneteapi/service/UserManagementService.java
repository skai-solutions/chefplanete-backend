package net.skai.chefplaneteapi.service;

import net.skai.chefplaneteapi.domain.User;
import net.skai.chefplaneteapi.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserManagementService implements UserService {

    private final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

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
            LOGGER.info("User: " + userId + " was deleted.");
            return true;
        }
        return false;
    }

    @Override
    public boolean addUser(@NotNull final User user) {
        if (!userRepository.existsById(user.getUserId())) {
            userRepository.save(user);
            LOGGER.info("User: " + user.getUserId() + " was registered.");
            return true;
        }
        return false;
    }

    @Override
    public boolean userExists(@NotNull final String userId) {
        return userRepository.existsById(userId);
    }
}
