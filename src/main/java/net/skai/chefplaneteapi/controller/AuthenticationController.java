package net.skai.chefplaneteapi.controller;

import net.skai.chefplaneteapi.domain.response.AuthResponse;
import net.skai.chefplaneteapi.service.AuthService;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Authenticates users and handles registering and JWT sessions.
 */
@RestController
public class AuthenticationController {

    private final AuthService authService;

    public AuthenticationController(@NotNull final AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/auth/user")
    public Principal authDetails(@NotNull final Principal principal) {
        return principal;
    }

    @GetMapping("/auth/signin/{idToken}")
    public AuthResponse signIn(@NotNull @PathVariable final String idToken) {
        return authService.login(idToken);
    }
}
