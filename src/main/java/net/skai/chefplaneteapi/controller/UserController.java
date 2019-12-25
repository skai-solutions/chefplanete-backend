package net.skai.chefplaneteapi.controller;

import net.skai.chefplaneteapi.domain.User;
import net.skai.chefplaneteapi.exception.AuthenticationException;
import net.skai.chefplaneteapi.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserController extends AbstractApiController {

    private final UserService userService;

    public UserController(@NotNull final UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{userId}")
    public User getUserDetails(@NotNull @PathVariable("userId") final String userId,
                               @NotNull final Principal principal) {
        if (principal.getName().equals(userId)) {
            return userService.getUserById(userId);
        }
        throw new AuthenticationException("You are not authorized for this action.", HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/user/{userId}")
    public void deleteUser(@NotNull @PathVariable("userId") final String userId,
                           @NotNull final Principal principal) {
        if (principal.getName().equals(userId)) {
            userService.deleteUserById(userId);
        }
        throw new AuthenticationException("You are not authorized for this action.", HttpStatus.UNAUTHORIZED);
    }
}
