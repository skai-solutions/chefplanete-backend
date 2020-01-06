package net.skai.chefplaneteapi.service;

import net.skai.chefplaneteapi.domain.Ingredient;
import net.skai.chefplaneteapi.domain.Pantry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public interface PantryService {

    @Nullable
    Pantry getPantryByUserId(@NotNull final String userId);

    boolean deletePantryByUserId(@NotNull final String userId);

    boolean addPantry(@NotNull final String userId);

    boolean updatePantry(@NotNull final String userId, @NotNull final Map<String, Ingredient> ingredients);
}
