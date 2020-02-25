package net.skai.chefplaneteapi.domain;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class Recipe {

    private final String recipeId;

    private final String recipeName;

    private final String recipeImageUrl;

    private final Integer recipeCookTime;

    private final Map<String, Ingredient> ingredients;

    public Recipe(final String recipeId,
                  final String recipeName,
                  final String recipeImageUrl,
                  final Integer recipeCookTime,
                  final Map<String, Ingredient> ingredients) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.recipeImageUrl = recipeImageUrl;
        this.recipeCookTime = recipeCookTime;
        this.ingredients = ingredients;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public Integer getRecipeCookTime() {
        return recipeCookTime;
    }

    public String getRecipeImageUrl() {
        return recipeImageUrl;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public Map<String, Ingredient> getIngredients() {
        return ingredients;
    }
}
