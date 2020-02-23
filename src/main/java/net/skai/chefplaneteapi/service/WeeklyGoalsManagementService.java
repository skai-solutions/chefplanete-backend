package net.skai.chefplaneteapi.service;

import net.skai.chefplaneteapi.domain.*;
import net.skai.chefplaneteapi.repository.WeeklyGoalsRepository;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WeeklyGoalsManagementService implements WeeklyGoalsService {

    private final WeeklyGoalsRepository weeklyGoalsRepository;

    private final PantryService pantryService;

    private final DietaryProfileService dietaryProfileService;

    private static final Logger LOGGER = LoggerFactory.getLogger(WeeklyGoalsManagementService.class);

    public WeeklyGoalsManagementService(@NotNull final WeeklyGoalsRepository weeklyGoalsRepository,
                                        @NotNull final PantryService pantryService,
                                        @NotNull final DietaryProfileService dietaryProfileService) {
        this.weeklyGoalsRepository = weeklyGoalsRepository;
        this.pantryService = pantryService;
        this.dietaryProfileService = dietaryProfileService;
    }

    @Override
    public @Nullable WeeklyGoals getWeeklyGoalsByUserId(@NotNull final String userId) {
        return weeklyGoalsRepository.findById(userId).orElse(null);
    }

    @Override
    public boolean deleteWeeklyGoalsByUserId(@NotNull final String userId) {
        if (weeklyGoalsRepository.existsById(userId)) {
            weeklyGoalsRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    @Override
    public boolean addWeeklyGoals(@NotNull final String userId) {
        if (!weeklyGoalsRepository.existsById(userId)) {
            weeklyGoalsRepository.save(new WeeklyGoals(userId, new ArrayList<>()));
            return true;
        }
        return false;
    }

    @Override
    public boolean addNewWeeklyGoal(@NotNull final String userId,
                                    @NotNull final Goal goal) {
        if (weeklyGoalsRepository.existsById(userId)) {
            final WeeklyGoals weeklyGoals = weeklyGoalsRepository.findById(userId).get();
            if (weeklyGoals.getGoals().stream().noneMatch(g -> g.getGoalId().equals(goal.getGoalId()))) {
                weeklyGoals.addNewGoal(goal);
                weeklyGoalsRepository.save(weeklyGoals);
                return true;
            }
            return false;

        }
        return false;
    }

    @Override
    public boolean deleteGoalByGoalId(@NotNull final String userId,
                                      @NotNull final String goalId) {
        if (weeklyGoalsRepository.existsById(userId)) {
            final WeeklyGoals weeklyGoals = weeklyGoalsRepository.findById(userId).get();
            final List<Goal> goals = weeklyGoals.getGoals();
            boolean isRemoved = goals.removeIf(g -> g.getGoalId().equals(goalId));
            weeklyGoalsRepository.save(weeklyGoals);
            return isRemoved;
        }
        return false;
    }

    @Override
    public boolean updateExistingGoal(@NotNull final String userId,
                                      @NotNull final Goal goal) {
        if (weeklyGoalsRepository.existsById(userId)) {
            final WeeklyGoals weeklyGoals = weeklyGoalsRepository.findById(userId).get();
            final Optional<Goal> goalOptional = weeklyGoals.getGoals().stream().filter(g -> g.getGoalId().equals(goal.getGoalId())).findAny();
            if (goalOptional.isPresent()) {
                final Goal oldGoal = goalOptional.get();
                oldGoal.setGoalType(goal.getGoalType() != null ? goal.getGoalType() : oldGoal.getGoalType());
                oldGoal.setRecipe(goal.getRecipe() != null ? goal.getRecipe() : oldGoal.getRecipe());
                weeklyGoalsRepository.save(weeklyGoals);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean completeWeeklyGoal(@NotNull final String userId,
                                      @NotNull final String goalId) {
        if (weeklyGoalsRepository.existsById(userId)) {
            final WeeklyGoals weeklyGoals = weeklyGoalsRepository.findById(userId).get();
            final Optional<Goal> goal = weeklyGoals.getGoals().stream().filter(g -> g.getGoalId().equals(goalId)).findAny();
            if (goal.isPresent()) {
                final Goal updatedGoal = goal.get();
                updatedGoal.setComplete(true);
                final Recipe recipe = updatedGoal.getRecipe();
                final Pantry pantry = pantryService.getPantryByUserId(userId);
                final Map<String, Ingredient> inventory = pantry.getInventory();
                final Map<String, Ingredient> updateInventory = new HashMap<>();
                recipe.getIngredients().forEach((s, ingredient) -> {
                    Ingredient i = inventory.get(s);
                    if (i == null) {
                        LOGGER.warn("Goal completed with insufficient " + ingredient.getName());
                    } else {
                        i.setQuantity(i.getQuantity() - ingredient.getQuantity());
                    }
                    updateInventory.put(s, i);
                });
                weeklyGoalsRepository.save(weeklyGoals);
                pantryService.updatePantry(userId, updateInventory);
                final DietaryProfile profile = dietaryProfileService.getDietaryProfileById(userId);
                profile.setTotalGoalsCompleted(profile.getTotalGoalsCompleted() + 1);
                dietaryProfileService.updateDietaryProfile(profile);
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean resetGoalStatuses(@NotNull final String userId) {
        if (weeklyGoalsRepository.existsById(userId)) {
            final WeeklyGoals weeklyGoals = weeklyGoalsRepository.findById(userId).get();
            weeklyGoals.getGoals().forEach(goal -> goal.setComplete(false));
            weeklyGoalsRepository.save(weeklyGoals);
            return true;
        }
        return false;
    }
}
