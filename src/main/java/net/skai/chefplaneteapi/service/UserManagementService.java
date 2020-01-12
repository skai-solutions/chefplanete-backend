package net.skai.chefplaneteapi.service;

import net.skai.chefplaneteapi.domain.DietaryProfile;
import net.skai.chefplaneteapi.domain.Pantry;
import net.skai.chefplaneteapi.domain.User;
import net.skai.chefplaneteapi.domain.WeeklyGoals;
import net.skai.chefplaneteapi.repository.DietaryProfileRepository;
import net.skai.chefplaneteapi.repository.PantryRepository;
import net.skai.chefplaneteapi.repository.UserRepository;
import net.skai.chefplaneteapi.repository.WeeklyGoalsRepository;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class UserManagementService implements UserService {

    private final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final DietaryProfileRepository dietaryProfileRepository;

    private final PantryRepository pantryRepository;

    private final WeeklyGoalsRepository weeklyGoalsRepository;

    public UserManagementService(@NotNull final UserRepository userRepository,
                                 @NotNull final DietaryProfileRepository dietaryProfileRepository,
                                 @NotNull final PantryRepository pantryRepository,
                                 @NotNull final WeeklyGoalsRepository weeklyGoalsRepository) {
        this.userRepository = userRepository;
        this.dietaryProfileRepository = dietaryProfileRepository;
        this.pantryRepository = pantryRepository;
        this.weeklyGoalsRepository = weeklyGoalsRepository;
    }

    @Override
    public @Nullable User getUserById(@NotNull final String userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public boolean deleteUserById(@NotNull final String userId) {
        if (userRepository.existsById(userId)) {
            weeklyGoalsRepository.deleteById(userId);
            LOGGER.info("User: " + userId + " Weekly Goals cleared.");
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
            weeklyGoalsRepository.save(new WeeklyGoals(user.getUserId(), new ArrayList<>()));
            LOGGER.info("Empty Goals list was created.");
            return true;
        }
        return false;
    }

    @Override
    public boolean userExists(@NotNull final String userId) {
        return userRepository.existsById(userId);
    }
}
