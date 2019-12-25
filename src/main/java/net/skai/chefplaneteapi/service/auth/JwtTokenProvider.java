package net.skai.chefplaneteapi.service.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.skai.chefplaneteapi.domain.Role;
import net.skai.chefplaneteapi.exception.JwtTokenException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    private final String secretKey;

    private final long validityInMilliseconds;

    private final MyUserDetails userDetails;

    public JwtTokenProvider(@NotNull @Value("${jwt.token.secretKey:secretKey}") final String secretKey,
                            @Value("${jwt.token.expireLength:0}") final long validityInMilliseconds,
                            @NotNull final MyUserDetails userDetails) {
        this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        this.validityInMilliseconds = validityInMilliseconds;
        this.userDetails = userDetails;
    }

    @NotNull
    public String createToken(@NotNull final String userId, @NotNull final List<Role> roles) {
        final Claims claims = Jwts.claims().setSubject(userId);
        claims.put("auth", roles.stream()
                                .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
                                .filter(Objects::nonNull)
                                .collect(Collectors.toList()));
        final Date currentDate = new Date();
        final Date expirationDate =
                validityInMilliseconds != 0 ? new Date(currentDate.getTime() + validityInMilliseconds) : null;
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(currentDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    @NotNull
    public Authentication getAuthentication(@NotNull final String token) {
        final UserDetails extractedDetails = userDetails.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(extractedDetails, "", extractedDetails.getAuthorities());
    }

    @NotNull
    public String getUsername(@NotNull final String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(@NotNull final HttpServletRequest request) {
        final String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(@NotNull final String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtTokenException("Expired or invalid JWT token.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
