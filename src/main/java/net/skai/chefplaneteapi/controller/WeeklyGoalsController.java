package net.skai.chefplaneteapi.controller;

import net.skai.chefplaneteapi.domain.Goal;
import net.skai.chefplaneteapi.domain.WeeklyGoals;
import net.skai.chefplaneteapi.exception.AuthenticationException;
import net.skai.chefplaneteapi.exception.UserNotFoundException;
import net.skai.chefplaneteapi.service.WeeklyGoalsService;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
public class WeeklyGoalsController extends AbstractApiController {

    private final WeeklyGoalsService weeklyGoalsService;

    public WeeklyGoalsController(@NotNull final WeeklyGoalsService weeklyGoalsService) {
        this.weeklyGoalsService = weeklyGoalsService;
    }

    @GetMapping("/user/{userId}/goals")
    public WeeklyGoals getWeeklyGoals(@NotNull @PathVariable("userId") final String userId,
                                      @NotNull final Principal principal) {
        if (principal.getName().equals(userId)) {
            return weeklyGoalsService.getWeeklyGoalsByUserId(userId);
        }
        throw new AuthenticationException("You are not authorized for this action.", HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/user/{userId}/goals")
    public void createNewWeeklyGoal(@NotNull @PathVariable("userId") final String userId,
                                    @NotNull @RequestBody final Goal goal,
                                    @NotNull final Principal principal) {
        if (principal.getName().equals(userId)) {
            if (!weeklyGoalsService.addNewWeeklyGoal(userId, goal)) {
                throw new UserNotFoundException(userId);
            }
            return;
        }
        throw new AuthenticationException("You are not authorized for this action.", HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/user/{userId}/goals/{goalId}")
    public void deleteWeeklyGoalById(@NotNull @PathVariable("userId") final String userId,
                                     @NotNull @PathVariable("goalId") final String goalId,
                                     @NotNull final Principal principal) {
        if (principal.getName().equals(userId)) {
            if (!weeklyGoalsService.deleteGoalByGoalId(userId, goalId)) {
                throw new UserNotFoundException(userId);
            }
            return;
        }
        throw new AuthenticationException("You are not authorized for this action.", HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/user/{userId}/goals/{goalId}")
    public void updateGoalById(@NotNull @PathVariable("userId") final String userId,
                               @NotNull @PathVariable("goalId") final String goalId,
                               @NotNull @RequestBody final Goal goal,
                               @NotNull final Principal principal) {
        if (principal.getName().equals(userId)) {
            goal.setGoalId(goalId);
            if (!weeklyGoalsService.updateExistingGoal(userId, goal)) {
                throw new UserNotFoundException(userId);
            }
            return;
        }
        throw new AuthenticationException("You are not authorized for this action.", HttpStatus.UNAUTHORIZED);
    }

    @PatchMapping("/user/{userId}/goals/{goalId}")
    public void completeGoalById(@NotNull @PathVariable("userId") final String userId,
                                 @NotNull @PathVariable("goalId") final String goalId,
                                 @NotNull final Principal principal) {
        if (principal.getName().equals(userId)) {
            if (!weeklyGoalsService.completeWeeklyGoal(userId, goalId)) {
                throw new UserNotFoundException(userId);
            }
            return;
        }
        throw new AuthenticationException("You are not authorized for this action.", HttpStatus.UNAUTHORIZED);
    }

    @PatchMapping("/user/{userId}/goals/reset")
    public void resetGoals(@NotNull @PathVariable("userId") final String userId,
                           @NotNull final Principal principal) {
        if (principal.getName().equals(userId)) {
            if (!weeklyGoalsService.resetGoalStatuses(userId)) {
                throw new UserNotFoundException(userId);
            }
            return;
        }
        throw new AuthenticationException("You are not authorized for this action.", HttpStatus.UNAUTHORIZED);
    }
}
