package net.skai.chefplaneteapi.domain;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DietaryProfile {

    @Id
    private final String userId;

    @Nullable
    private Integer cookingLevel;

    @Nullable
    private Integer totalGoalsCompleted;

    @Nullable
    private List<String> foodRestrictions;

    public DietaryProfile(@NotNull final String userId,
                          @Nullable final Integer cookingLevel,
                          @Nullable final Integer totalGoalsCompleted,
                          @Nullable final List<String> foodRestrictions) {
        this.userId = userId;
        this.cookingLevel = cookingLevel;
        this.totalGoalsCompleted = totalGoalsCompleted;
        this.foodRestrictions = foodRestrictions;
    }

    public DietaryProfile(@NotNull final String userId,
                          @Nullable final Integer cookingLevel) {
        this.userId = userId;
        this.cookingLevel = cookingLevel;
        this.totalGoalsCompleted = 0;
        this.foodRestrictions = new ArrayList<>();
    }

    public DietaryProfile(@NotNull final String userId) {
        this.userId = userId;
        this.cookingLevel = 0;
        this.totalGoalsCompleted = 0;
        this.foodRestrictions = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    @Nullable
    public Integer getCookingLevel() {
        return cookingLevel;
    }

    public void setCookingLevel(Integer cookingLevel) {
        this.cookingLevel = cookingLevel;
    }

    @Nullable
    public Integer getTotalGoalsCompleted() {
        return totalGoalsCompleted;
    }

    public void setTotalGoalsCompleted(Integer totalGoalsCompleted) {
        this.totalGoalsCompleted = totalGoalsCompleted;
    }

    @Nullable
    public List<String> getFoodRestrictions() {
        return foodRestrictions;
    }

    public void setFoodRestrictions(List<String> foodRestrictions) {
        this.foodRestrictions = foodRestrictions;
    }

    @Override
    public String toString() {
        return "DietaryProfile{" +
                "userId='" + userId + '\'' +
                ", cookingLevel=" + cookingLevel +
                ", totalGoalsCompleted=" + totalGoalsCompleted +
                ", foodRestrictions=" + foodRestrictions +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DietaryProfile that = (DietaryProfile) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(cookingLevel, that.cookingLevel) &&
                Objects.equals(totalGoalsCompleted, that.totalGoalsCompleted) &&
                Objects.equals(foodRestrictions, that.foodRestrictions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, cookingLevel, totalGoalsCompleted, foodRestrictions);
    }
}
