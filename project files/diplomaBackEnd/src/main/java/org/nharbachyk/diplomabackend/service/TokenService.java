package org.nharbachyk.diplomabackend.service;

import io.jsonwebtoken.Claims;
import org.nharbachyk.diplomabackend.controller.response.TokenResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.function.Function;

public interface TokenService {

    public static final String BEARER_TOKEN = "Bearer";

    String getRefreshToken(String username);

    void saveRefreshToken(String username, String refreshToken);

    void deleteRefreshToken(String username);

    String getAccessToken(String username);

    void saveAccessToken(String username, String refreshToken);

    void deleteAccessToken(String username);

    TokenResponse generateTokens(String username);

    String extractUsername(String token);

    Date extractExpiration(String token);

    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    Boolean isTokenExpired(String token);

    Boolean validateToken(String token, UserDetails userDetails) throws AuthenticationException;

    boolean validateRefreshToken(String username, String refreshToken);

    void invalidateTokens(String username);

    TokenResponse refreshTokens(String username, String refreshToken);
}

