package net.skai.chefplaneteapi.domain;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class Recipe {

    private final String recipeId;

    private final Map<String, Ingredient> ingredients;

    public Recipe(@NotNull final String recipeId,
                  @NotNull final Map<String, Ingredient> ingredients) {
        this.recipeId = recipeId;
        this.ingredients = ingredients;
    }

    @NotNull
    public String getRecipeId() {
        return recipeId;
    }

    @NotNull
    public Map<String, Ingredient> getIngredients() {
        return ingredients;
    }
}
