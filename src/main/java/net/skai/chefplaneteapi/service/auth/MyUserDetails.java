package net.skai.chefplaneteapi.service.auth;

import net.skai.chefplaneteapi.domain.Role;
import net.skai.chefplaneteapi.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class MyUserDetails implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(@NotNull final String userId) throws UsernameNotFoundException {
        return User
                .withUsername(userId)
                .password(userId)
                .authorities(Collections.singleton(Role.ROLE_CLIENT))
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
//        throw new UsernameNotFoundException("User: " + userId + " could not be found.");
    }
}
