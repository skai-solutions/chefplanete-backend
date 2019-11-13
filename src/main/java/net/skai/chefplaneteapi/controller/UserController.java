package net.skai.chefplaneteapi.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserController extends AbstractApiController {

    @GetMapping("/user")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_USER')")
    public Principal user(@NotNull final Principal principal) {
        return principal;
    }

    @GetMapping("/user/test")
    @ResponseBody
    public String test() {
        return "Unsecured Test.";
    }
}
