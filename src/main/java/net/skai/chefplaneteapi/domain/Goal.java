package net.skai.chefplaneteapi.domain;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class Goal {

    private String goalId;

    private GoalType goalType;

    private boolean isComplete;

    private Recipe recipe;

    public Goal(@Nullable final String goalId,
                @Nullable final GoalType goalType,
                final boolean isComplete,
                @Nullable final Recipe recipe) {
        this.goalId = goalId;
        this.goalType = goalType;
        this.recipe = recipe;
        this.isComplete = isComplete;
    }

    @Nullable
    public String getGoalId() {
        return goalId;
    }

    public void setGoalId(@Nullable final String goalId) {
        this.goalId = goalId;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public boolean isComplete() {
        return isComplete;
    }

    @Nullable
    public GoalType getGoalType() {
        return goalType;
    }

    public void setGoalType(@NotNull final GoalType goalType) {
        this.goalType = goalType;
    }

    @Nullable
    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(@NotNull final Recipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Goal goal = (Goal) o;
        return isComplete == goal.isComplete &&
                goalType == goal.goalType &&
                recipe.equals(goal.recipe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(goalType, isComplete, recipe);
    }
}
