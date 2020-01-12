package net.skai.chefplaneteapi.service;

import net.skai.chefplaneteapi.domain.Goal;
import net.skai.chefplaneteapi.domain.WeeklyGoals;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface WeeklyGoalsService {

    @Nullable
    WeeklyGoals getWeeklyGoalsByUserId(@NotNull final String userId);

    boolean deleteWeeklyGoalsByUserId(@NotNull final String userId);

    boolean addWeeklyGoals(@NotNull final String userId);

    boolean addNewWeeklyGoal(@NotNull final String userId, @NotNull final Goal goal);

    boolean deleteGoalByGoalId(@NotNull final String userId, @NotNull final String goalId);

    boolean updateExistingGoal(@NotNull final String userId, @NotNull final Goal goal);

    boolean completeWeeklyGoal(@NotNull final String userId, @NotNull final String goalId);

    boolean resetGoalStatuses(@NotNull final String userId);
}
