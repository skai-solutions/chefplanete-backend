package net.skai.chefplaneteapi.service;

import net.skai.chefplaneteapi.domain.Ingredient;
import net.skai.chefplaneteapi.domain.Pantry;
import net.skai.chefplaneteapi.repository.PantryRepository;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PantryInventoryService implements PantryService {

    private final PantryRepository pantryRepository;

    public PantryInventoryService(@NotNull final PantryRepository pantryRepository) {
        this.pantryRepository = pantryRepository;
    }

    @Override
    public @Nullable Pantry getPantryByUserId(@NotNull final String userId) {
        return pantryRepository.findById(userId).orElse(null);
    }

    @Override
    public boolean deletePantryByUserId(@NotNull final String userId) {
        if (pantryRepository.existsById(userId)) {
            pantryRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    @Override
    public boolean addPantry(@NotNull final String userId) {
        if (!pantryRepository.existsById(userId)) {
            pantryRepository.save(new Pantry(userId, new HashMap<>()));
            return true;
        }
        return false;
    }

    @Override
    public boolean updatePantry(@NotNull final String userId,
                                @NotNull final Map<String, Ingredient> ingredientUpdates) {
        if (pantryRepository.existsById(userId)) {
            final Pantry pantry = pantryRepository.findById(userId).orElse(new Pantry(userId, new HashMap<>()));
            final Map<String, Ingredient> inventory = pantry.getInventory();
            ingredientUpdates.forEach((key, value) -> {
                if (value.getQuantity() <= 0) {
                    inventory.remove(key);
                }
                else {
                    inventory.put(key, value);
                }
            });
            pantryRepository.save(pantry);
            return true;
        }
        return false;
    }
}
