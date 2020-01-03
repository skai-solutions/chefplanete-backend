package net.skai.chefplaneteapi.service;

import net.skai.chefplaneteapi.domain.DietaryProfile;
import org.jetbrains.annotations.NotNull;

public interface DietaryProfileService {

    DietaryProfile getDietaryProfileById(@NotNull String userId);

    boolean deleteDietaryProfileById(@NotNull String userId);

    boolean addDietaryProfile(@NotNull DietaryProfile dietaryProfile);

    boolean updateDietaryProfile(@NotNull DietaryProfile dietaryProfile);
}
