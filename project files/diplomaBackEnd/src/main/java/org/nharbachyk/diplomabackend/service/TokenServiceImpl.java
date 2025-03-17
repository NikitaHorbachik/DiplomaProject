package org.nharbachyk.diplomabackend.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.nharbachyk.diplomabackend.controller.response.TokenResponse;
import org.nharbachyk.diplomabackend.entities.redis.AccessTokenEntity;
import org.nharbachyk.diplomabackend.entities.redis.RefreshTokenEntity;
import org.nharbachyk.diplomabackend.exceptions.InvalidJwtTokenException;
import org.nharbachyk.diplomabackend.repository.redis.AccessTokenRepository;
import org.nharbachyk.diplomabackend.repository.redis.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final AccessTokenRepository accessTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private SecretKey secretKey;
    private Duration accessExpirationDuration;
    private Duration refreshExpirationDuration;

    @Value("${security.access-token-expiration}")
    private int accessTokenExpirationInHours;

    @Value("${security.refresh-token-expiration}")
    private int refreshTokenExpirationInDays;

    @Value("${security.access-secret-key-string}")
    private byte[] accessSecretKeyValue;

    @Override
    public TokenResponse generateTokens(String username) {
        if (accessTokenRepository.existsByUsername(username) || refreshTokenRepository.existsByUsername(username)) {
            invalidateTokens(username);
        }
        String accessToken = generateToken(username, accessExpirationDuration.toMillis());
        saveAccessToken(username, accessToken);
        String refreshToken = generateToken(username, refreshExpirationDuration.toMillis());
        saveRefreshToken(username, refreshToken);
        return new TokenResponse(accessToken, refreshToken);
    }

    @Override
    public String extractUsername(String token) throws MalformedJwtException, SignatureException {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public Date extractExpiration(String token) throws MalformedJwtException, SignatureException {
        return extractClaim(token, Claims::getExpiration);
    }

    @Override
    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    @Override
    public boolean validateToken(String token, String username) throws AuthenticationException {
        String tokenUsername = extractUsername(token);
        return username.equals(tokenUsername) && !isTokenExpired(token);
    }

    @Override
    public boolean validateAccessToken(String token, String username) throws AuthenticationException {
        String storedToken = getAccessTokenOrThrow(username);
        return token.equals(storedToken);
    }

    @Override
    public boolean validateRefreshToken(String username, String refreshToken) {
        String storedToken = getRefreshTokenOrThrow(username);
        return refreshToken.equals(storedToken);
    }

    @Override
    public void invalidateTokens(String username) {
        deleteAccessToken(username);
        deleteRefreshToken(username);
    }

    @Override
    public TokenResponse refreshTokens(String username, String refreshToken) {
        if (!validateRefreshToken(username, refreshToken)) {
            throw new AuthenticationException("Invalid refresh token") {
            };
        }
        return generateTokens(username);
    }

    @PostConstruct
    private void init() {
        secretKey = Keys.hmacShaKeyFor(accessSecretKeyValue);
        accessSecretKeyValue = null;
        accessExpirationDuration = Duration.ofHours(accessTokenExpirationInHours);
        refreshExpirationDuration = Duration.ofDays(refreshTokenExpirationInDays);
    }

    private void saveAccessToken(String username, String accessToken) {
        AccessTokenEntity accessTokenEntity = new AccessTokenEntity();
        accessTokenEntity.setUsername(username);
        accessTokenEntity.setToken(accessToken);
        accessTokenRepository.save(accessTokenEntity);
    }

    private void saveRefreshToken(String username, String refreshToken) {
        RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity();
        refreshTokenEntity.setUsername(username);
        refreshTokenEntity.setToken(refreshToken);
        refreshTokenRepository.save(refreshTokenEntity);
    }

    private String getAccessTokenOrThrow(String username) {
        return accessTokenRepository.findById(username)
                .orElseThrow(InvalidJwtTokenException::new)
                .getToken();
    }

    private String getRefreshTokenOrThrow(String username) {
        return refreshTokenRepository.findById(username)
                .orElseThrow(InvalidJwtTokenException::new)
                .getToken();
    }

    private void deleteAccessToken(String username) {
        accessTokenRepository.deleteById(username);
    }

    private void deleteRefreshToken(String username) {
        refreshTokenRepository.deleteById(username);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) throws MalformedJwtException, SignatureException {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) throws MalformedJwtException, SignatureException {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private String generateToken(String username, Long expirationMs) {
        long currentTimeMillis = System.currentTimeMillis();
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date(currentTimeMillis))
                .expiration(new Date(currentTimeMillis + expirationMs))
                .signWith(secretKey)
                .compact();
    }

}
