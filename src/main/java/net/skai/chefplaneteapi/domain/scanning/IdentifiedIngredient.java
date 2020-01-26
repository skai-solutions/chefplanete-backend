package net.skai.chefplaneteapi.domain.scanning;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IdentifiedIngredient that = (IdentifiedIngredient) o;
        return isUnsure == that.isUnsure &&
                Objects.equals(ingredientName, that.ingredientName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ingredientName, isUnsure);
    }
}
