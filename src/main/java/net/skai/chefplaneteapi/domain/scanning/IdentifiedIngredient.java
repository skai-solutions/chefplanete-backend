package net.skai.chefplaneteapi.domain.scanning;

import org.jetbrains.annotations.NotNull;

public class IdentifiedIngredient {

    private final String ingredientName;

    private final boolean isUnsure;

    public IdentifiedIngredient(@NotNull final String ingredientName, final boolean isUnsure) {
        this.ingredientName = ingredientName;
        this.isUnsure = isUnsure;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public boolean isUnsure() {
        return isUnsure;
    }
}
