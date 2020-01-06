package net.skai.chefplaneteapi.domain;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;

import java.util.Map;

public class Pantry {

    @Id
    private final String userId;

    private final Map<String, Ingredient> inventory;

    public Pantry(@NotNull final String userId,
                  @NotNull final Map<String, Ingredient> inventory) {
        this.userId = userId;
        this.inventory = inventory;
    }

    public String getUserId() {
        return userId;
    }

    public Map<String, Ingredient> getInventory() {
        return inventory;
    }
}
