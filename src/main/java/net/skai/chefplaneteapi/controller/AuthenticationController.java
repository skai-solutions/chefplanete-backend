package net.skai.chefplaneteapi.controller;

import net.skai.chefplaneteapi.domain.response.AuthResponse;
import net.skai.chefplaneteapi.service.AuthService;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Authenticates users and handles registering and sessions
 */
@RestController
public class AuthenticationController {

    private AuthService authService;

    public AuthenticationController(@NotNull final AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/auth/user")
    public Principal authDetails(@NotNull final Principal principal) {
        return principal;
    }

    @GetMapping("/auth/signin/{idToken}")
    public ResponseEntity<AuthResponse> signIn(@NotNull @PathVariable final String idToken) {
        return new ResponseEntity<>(authService.login(idToken), HttpStatus.OK);
    }

    @GetMapping("/auth/signup/{idToken}")
    public ResponseEntity<AuthResponse> signUp(@NotNull @PathVariable final String idToken) {
        return new ResponseEntity<>(authService.signup(idToken), HttpStatus.OK);
    }
}
