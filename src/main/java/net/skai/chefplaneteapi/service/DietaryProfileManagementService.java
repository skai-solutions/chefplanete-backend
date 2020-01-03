package net.skai.chefplaneteapi.service;

import net.skai.chefplaneteapi.domain.DietaryProfile;
import net.skai.chefplaneteapi.repository.DietaryProfileRepository;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

@Service
public class DietaryProfileManagementService implements DietaryProfileService {

    public final DietaryProfileRepository dietaryProfileRepository;

    public DietaryProfileManagementService(@NotNull final DietaryProfileRepository dietaryProfileRepository) {
        this.dietaryProfileRepository = dietaryProfileRepository;
    }

    @Override
    public @Nullable DietaryProfile getDietaryProfileById(@NotNull final String userId) {
        return dietaryProfileRepository.findById(userId).orElse(null);
    }

    @Override
    public boolean deleteDietaryProfileById(@NotNull final String userId) {
        if (dietaryProfileRepository.existsById(userId)) {
            dietaryProfileRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    @Override
    public boolean addDietaryProfile(@NotNull final DietaryProfile dietaryProfile) {
        if (!dietaryProfileRepository.existsById(dietaryProfile.getUserId())) {
            dietaryProfileRepository.save(dietaryProfile);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateDietaryProfile(@NotNull final DietaryProfile dietaryProfile) {
        if (dietaryProfileRepository.existsById(dietaryProfile.getUserId())) {
            //noinspection OptionalGetWithoutIsPresent
            final DietaryProfile existingProfile = dietaryProfileRepository.findById(dietaryProfile.getUserId()).get();
            final DietaryProfile updatedProfile = new DietaryProfile(
                    dietaryProfile.getUserId(),
                    dietaryProfile.getCookingLevel() == null ? existingProfile.getCookingLevel() : dietaryProfile.getCookingLevel(),
                    dietaryProfile.getTotalGoalsCompleted() == null ? existingProfile.getTotalGoalsCompleted() : dietaryProfile.getTotalGoalsCompleted(),
                    dietaryProfile.getFoodRestrictions() == null ? existingProfile.getFoodRestrictions() : dietaryProfile.getFoodRestrictions()
            );
            dietaryProfileRepository.save(updatedProfile);
            return true;
        }
        return false;
    }
}
