package net.skai.chefplaneteapi.controller;

import net.skai.chefplaneteapi.domain.Ingredient;
import net.skai.chefplaneteapi.domain.Pantry;
import net.skai.chefplaneteapi.exception.AuthenticationException;
import net.skai.chefplaneteapi.exception.UserNotFoundException;
import net.skai.chefplaneteapi.service.PantryService;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
public class PantryController extends AbstractApiController {

    private final PantryService pantryService;

    public PantryController(@NotNull final PantryService pantryService) {
        this.pantryService = pantryService;
    }

    @GetMapping("/user/{userId}/pantry")
    public Pantry getPantry(@NotNull @PathVariable("userId") final String userId,
                            @NotNull final Principal principal) {
        if (principal.getName().equals(userId)) {
            return pantryService.getPantryByUserId(userId);
        }
        throw new AuthenticationException("You are not authorized for this action.", HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/user/{userId}/pantry")
    public void updatePantry(@NotNull @PathVariable("userId") final String userId,
                             @NotNull @RequestBody Map<String, Ingredient> pantryUpdate,
                             @NotNull final Principal principal) {
        if (principal.getName().equals(userId)) {
            if (!pantryService.updatePantry(userId, pantryUpdate)) {
                throw new UserNotFoundException(userId);
            }
            return;
        }
        throw new AuthenticationException("You are not authorized for this action.", HttpStatus.UNAUTHORIZED);
    }
}
