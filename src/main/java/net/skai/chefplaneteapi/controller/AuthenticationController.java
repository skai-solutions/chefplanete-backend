package net.skai.chefplaneteapi.controller;

import net.skai.chefplaneteapi.domain.response.AuthResponse;
import net.skai.chefplaneteapi.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Authenticates users and handles registering and sessions
 */
@RestController
public class AuthenticationController {

    private UserService userService;

    public AuthenticationController(@NotNull final UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/auth/user")
    public Principal authDetails(@NotNull final Principal principal) {
        return principal;
    }

    @GetMapping("/auth/signin/{idToken}")
    public ResponseEntity<AuthResponse> signIn(@NotNull @PathVariable final String idToken) {
        return new ResponseEntity<>(userService.login(idToken), HttpStatus.OK);
    }

    @GetMapping("/auth/signup/{idToken}")
    public ResponseEntity<AuthResponse> signUp(@NotNull @PathVariable final String idToken) {
        return new ResponseEntity<>(userService.signup(idToken), HttpStatus.OK);
    }
}
