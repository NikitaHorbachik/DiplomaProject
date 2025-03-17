package org.nharbachyk.diplomabackend.service;

import org.nharbachyk.diplomabackend.controller.response.TokenResponse;
import org.springframework.security.core.AuthenticationException;

import java.util.Date;

public interface TokenService {

    TokenResponse generateTokens(String username);

    String extractUsername(String token);

    Date extractExpiration(String token);

    Boolean isTokenExpired(String token);

    boolean validateToken(String token, String username) throws AuthenticationException;

    boolean validateAccessToken(String token, String username) throws AuthenticationException;

    boolean validateRefreshToken(String username, String refreshToken);

    void invalidateTokens(String username);

    TokenResponse refreshTokens(String username, String refreshToken);

}

