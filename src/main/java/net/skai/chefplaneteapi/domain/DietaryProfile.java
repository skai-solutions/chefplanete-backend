package net.skai.chefplaneteapi.domain;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DietaryProfile {

    @Id
    private final String userId;

    private Integer cookingLevel;

    private Integer totalGoalsCompleted;

    private List<String> foodRestrictions;

    public DietaryProfile(@NotNull final String userId,
                          @NotNull final Integer cookingLevel,
                          @NotNull final Integer totalGoalsCompleted,
                          @NotNull final List<String> foodRestrictions) {
        this.userId = userId;
        this.cookingLevel = cookingLevel;
        this.totalGoalsCompleted = totalGoalsCompleted;
        this.foodRestrictions = foodRestrictions;
    }

    public DietaryProfile(@NotNull final String userId,
                          @NotNull final Integer cookingLevel) {
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

    public Integer getCookingLevel() {
        return cookingLevel;
    }

    public void setCookingLevel(Integer cookingLevel) {
        this.cookingLevel = cookingLevel;
    }

    public Integer getTotalGoalsCompleted() {
        return totalGoalsCompleted;
    }

    public void setTotalGoalsCompleted(Integer totalGoalsCompleted) {
        this.totalGoalsCompleted = totalGoalsCompleted;
    }

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
