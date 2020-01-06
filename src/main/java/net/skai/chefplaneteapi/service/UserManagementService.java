package net.skai.chefplaneteapi.service;

import net.skai.chefplaneteapi.domain.DietaryProfile;
import net.skai.chefplaneteapi.domain.Pantry;
import net.skai.chefplaneteapi.domain.User;
import net.skai.chefplaneteapi.repository.DietaryProfileRepository;
import net.skai.chefplaneteapi.repository.PantryRepository;
import net.skai.chefplaneteapi.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UserManagementService implements UserService {

    private final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final DietaryProfileRepository dietaryProfileRepository;

    private final PantryRepository pantryRepository;

    public UserManagementService(@NotNull final UserRepository userRepository,
                                 @NotNull final DietaryProfileRepository dietaryProfileRepository,
                                 @NotNull final PantryRepository pantryRepository) {
        this.userRepository = userRepository;
        this.dietaryProfileRepository = dietaryProfileRepository;
        this.pantryRepository = pantryRepository;
    }

    @Override
    public @Nullable User getUserById(@NotNull final String userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public boolean deleteUserById(@NotNull final String userId) {
        if (userRepository.existsById(userId)) {
            pantryRepository.deleteById(userId);
            LOGGER.info("User: " + userId + " inventory cleared.");
            dietaryProfileRepository.deleteById(userId);
            LOGGER.info("User: " + userId + " profile cleared.");
            userRepository.deleteById(userId);
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
            dietaryProfileRepository.save(new DietaryProfile(user.getUserId()));
            LOGGER.info("Blank Dietary Profile created.");
            pantryRepository.save(new Pantry(user.getUserId(), new HashMap<>()));
            LOGGER.info("Empty Pantry (Inventory) created.");
            return true;
        }
        return false;
    }

    @Override
    public boolean userExists(@NotNull final String userId) {
        return userRepository.existsById(userId);
    }
}
