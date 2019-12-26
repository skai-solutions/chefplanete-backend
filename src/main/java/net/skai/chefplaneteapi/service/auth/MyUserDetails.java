package net.skai.chefplaneteapi.service.auth;

import net.skai.chefplaneteapi.domain.Role;
import net.skai.chefplaneteapi.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class MyUserDetails implements UserDetailsService {

    private UserRepository userRepository;

    public MyUserDetails(@NotNull final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(@NotNull final String userId) throws UsernameNotFoundException {
        if (userRepository.existsById(userId)) {
            return User
                    .withUsername(userId)
                    .password("NONE")
                    .authorities(Collections.singleton(Role.ROLE_CLIENT))
                    .accountExpired(false)
                    .accountLocked(false)
                    .credentialsExpired(false)
                    .disabled(false)
                    .build();
        } else {
            throw new UsernameNotFoundException("User: " + userId + " could not be found.");
        }
    }
}
