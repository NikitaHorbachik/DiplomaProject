package org.nharbachyk.diplomabackend.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.nharbachyk.diplomabackend.service.TokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtUtils {

    private final TokenService tokenService;

    private SecretKey secretKey;
    private Duration accessExpirationDuration;
    private Duration refreshExpirationDuration;

    public static final String BEARER_TOKEN = "Bearer";

    @Value("${security.access-token-expiration}")
    private int accessTokenExpirationInHours;

    @Value("${security.refresh-token-expiration}")
    private int refreshTokenExpirationInDays;

    @Value("${security.access-secret-key-string}")
    private byte[] accessSecretKeyValue;

    @PostConstruct
    private void init() {
        secretKey = Keys.hmacShaKeyFor(accessSecretKeyValue);
        accessSecretKeyValue = null;
        accessExpirationDuration = Duration.ofHours(accessTokenExpirationInHours);
        refreshExpirationDuration = Duration.ofDays(refreshTokenExpirationInDays);
    }

    public List<String> generateTokens(String username) {
        ArrayList<String> tokens = new ArrayList<>();
        String accessToken = generateAccessToken(username);
        String refreshToken = generateRefreshToken(username);

        // Сохраняем refresh-токен в Redis
        tokenService.saveRefreshToken(username, refreshToken);

        tokens.add(accessToken);
        tokens.add(refreshToken);
        return tokens;
    }

    public String generateAccessToken(String username) {
        long currentTimeMillis = System.currentTimeMillis();
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date(currentTimeMillis))
                .expiration(new Date(currentTimeMillis + accessExpirationDuration.toMillis()))
                .signWith(secretKey)
                .compact();
    }

    public String generateRefreshToken(String username) {
        long currentTimeMillis = System.currentTimeMillis();
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date(currentTimeMillis))
                .expiration(new Date(currentTimeMillis + refreshExpirationDuration.toMillis()))
                .signWith(secretKey)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) throws AuthenticationException {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public boolean validateRefreshToken(String username, String refreshToken) {
        String storedRefreshToken = tokenService.getRefreshToken(username);
        return refreshToken.equals(storedRefreshToken);
    }

    public void invalidateRefreshToken(String username) {
        tokenService.deleteRefreshToken(username);
    }

    public List<String> refreshTokens(String username, String refreshToken) {
        if (!validateRefreshToken(username, refreshToken)) {
            throw new AuthenticationException("Invalid refresh token") {};
        }
        tokenService.deleteRefreshToken(username);
        return generateTokens(username);
    }
}