package net.skai.chefplaneteapi.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import net.skai.chefplaneteapi.service.auth.JwtTokenFilterConfigurer;
import net.skai.chefplaneteapi.service.auth.JwtTokenProvider;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider tokenProvider;

    public SecurityConfiguration(@NotNull final JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests()
                .antMatchers("/auth/signin/**").permitAll()
                .antMatchers("/auth/signup/**").permitAll()
                .anyRequest().authenticated();
        http.apply(new JwtTokenFilterConfigurer(tokenProvider));
        http.httpBasic();
    }

    @Bean
    public GoogleIdTokenVerifier idTokenVerifier(@NotNull @Value("${oauth2.resource.clientId}") final String clientId) throws GeneralSecurityException, IOException {
        return new GoogleIdTokenVerifier
                .Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance())
                .setAudience(Collections.singletonList(clientId))
                .build();
    }
}
