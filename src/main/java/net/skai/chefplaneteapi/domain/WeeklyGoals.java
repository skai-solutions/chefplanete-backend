package net.skai.chefplaneteapi.domain;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;

import java.util.List;

public class WeeklyGoals {

    @Id
    private final String userId;

    private int goalSequenceNumber;

    private final List<Goal> goals;

    public WeeklyGoals(@NotNull final String userId,
                       @NotNull final List<Goal> goals) {
        this.userId = userId;
        this.goals = goals;
        this.goalSequenceNumber = 0;
    }

    @NotNull
    public String getUserId() {
        return userId;
    }

    @NotNull
    public List<Goal> getGoals() {
        return goals;
    }

    public void addNewGoal(@NotNull final Goal goal) {
        goal.setGoalId(Integer.toString(++goalSequenceNumber));
        goals.add(goal);
    }

    public int getGoalSequenceNumber() {
        return goalSequenceNumber;
    }
}
