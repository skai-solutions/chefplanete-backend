package net.skai.chefplaneteapi.controller;

import net.skai.chefplaneteapi.domain.DietaryProfile;
import net.skai.chefplaneteapi.exception.AuthenticationException;
import net.skai.chefplaneteapi.exception.UserNotFoundException;
import net.skai.chefplaneteapi.service.DietaryProfileService;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
public class DietaryProfileController extends AbstractApiController {

    private final DietaryProfileService dietaryProfileService;

    public DietaryProfileController(@NotNull final DietaryProfileService dietaryProfileService) {
        this.dietaryProfileService = dietaryProfileService;
    }

    @GetMapping("/user/{userId}/profile")
    public DietaryProfile getDietaryProfile(@NotNull @PathVariable("userId") final String userId,
                                            @NotNull final Principal principal) {
        if (principal.getName().equals(userId)) {
            return dietaryProfileService.getDietaryProfileById(userId);
        }
        throw new AuthenticationException("You are not authorized for this action.", HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/user/{userId}/profile")
    public void deleteProfile(@NotNull @PathVariable("userId") final String userId,
                              @NotNull final Principal principal) {
        if (principal.getName().equals(userId)) {
            if (!dietaryProfileService.deleteDietaryProfileById(userId)) {
                throw new UserNotFoundException(userId);
            }
            return;
        }
        throw new AuthenticationException("You are not authorized for this action.", HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/user/{userId}/profile")
    public void updateProfile(@NotNull @PathVariable("userId") final String userId,
                              @NotNull @RequestBody DietaryProfile dietaryProfile,
                              @NotNull final Principal principal) {
        if (principal.getName().equals(userId)) {
            if (!dietaryProfileService.updateDietaryProfile(dietaryProfile)) {
                throw new UserNotFoundException(dietaryProfile.getUserId());
            }
            return;
        }
        throw new AuthenticationException("You are not authorized for this action.", HttpStatus.UNAUTHORIZED);
    }
}
