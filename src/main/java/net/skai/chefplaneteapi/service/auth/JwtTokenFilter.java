package net.skai.chefplaneteapi.service.auth;

import net.skai.chefplaneteapi.service.auth.exception.AuthenticationException;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenFilter extends OncePerRequestFilter {

    private JwtTokenProvider tokenProvider;

    public JwtTokenFilter(@NotNull final JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(@NotNull final HttpServletRequest httpServletRequest,
                                    @NotNull final HttpServletResponse httpServletResponse,
                                    @NotNull final FilterChain filterChain) throws ServletException, IOException {
        final String token = tokenProvider.resolveToken(httpServletRequest);
        try {
            if (token != null && tokenProvider.validateToken(token)) {
                final Authentication authentication = tokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (AuthenticationException e) {
            SecurityContextHolder.clearContext();
            httpServletResponse.sendError(e.getHttpStatus().value(), e.getMessage());
            return;
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
