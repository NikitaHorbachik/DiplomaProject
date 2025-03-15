package org.nharbachyk.diplomabackend.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.nharbachyk.diplomabackend.controller.response.TokenResponse;
import org.nharbachyk.diplomabackend.entities.AccessTokenEntity;
import org.nharbachyk.diplomabackend.entities.RefreshTokenEntity;
import org.nharbachyk.diplomabackend.exceptions.InvalidJwtTokenException;
import org.nharbachyk.diplomabackend.repository.redis.AccessTokenRepository;
import org.nharbachyk.diplomabackend.repository.redis.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final AccessTokenRepository accessTokenRepository;

    private SecretKey secretKey;
    private Duration accessExpirationDuration;
    private Duration refreshExpirationDuration;

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

    @Override
    public String getRefreshToken(String username) {
        return refreshTokenRepository.findByUsername(username)
                .orElseThrow(InvalidJwtTokenException::new)
                .getToken();
    }

    @Override
    public void saveRefreshToken(String username, String refreshToken) {
        RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity();
        refreshTokenEntity.setUsername(username);
        refreshTokenEntity.setToken(refreshToken);
        refreshTokenRepository.save(refreshTokenEntity);
    }

    @Override
    public void deleteRefreshToken(String username) {
        refreshTokenRepository.deleteByUsername(username);
    }

    @Override
    public String getAccessToken(String username) {
        return accessTokenRepository.findByUsername(username)
                .orElseThrow(InvalidJwtTokenException::new)
                .getToken();
    }

    @Override
    public void saveAccessToken(String username, String refreshToken) {
        AccessTokenEntity accessTokenEntity = new AccessTokenEntity();
        accessTokenEntity.setUsername(username);
        accessTokenEntity.setToken(refreshToken);
        accessTokenRepository.save(accessTokenEntity);
    }

    @Override
    public void deleteAccessToken(String username) {
        accessTokenRepository.deleteByUsername(username);
    }

    @Override
    public TokenResponse generateTokens(String username) {
        String accessToken = generateAccessToken(username);
        String refreshToken = generateRefreshToken(username);
        saveRefreshToken(username, refreshToken);
        saveAccessToken(username, accessToken);
        return new TokenResponse(accessToken, refreshToken);
    }

    private String generateAccessToken(String username) {
        long currentTimeMillis = System.currentTimeMillis();
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date(currentTimeMillis))
                .expiration(new Date(currentTimeMillis + accessExpirationDuration.toMillis()))
                .signWith(secretKey)
                .compact();
    }

    private String generateRefreshToken(String username) {
        long currentTimeMillis = System.currentTimeMillis();
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date(currentTimeMillis))
                .expiration(new Date(currentTimeMillis + refreshExpirationDuration.toMillis()))
                .signWith(secretKey)
                .compact();
    }

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    @Override
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

    @Override
    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    @Override
    public Boolean validateToken(String token, UserDetails userDetails) throws AuthenticationException {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    @Override
    public boolean validateRefreshToken(String username, String refreshToken) {
        String storedRefreshToken = getRefreshToken(username);
        return refreshToken.equals(storedRefreshToken);
    }

    @Override
    public void invalidateTokens(String username) {
        deleteRefreshToken(username);
        deleteAccessToken(username);
    }

    @Override
    public TokenResponse refreshTokens(String username, String refreshToken) {
        if (!validateRefreshToken(username, refreshToken)) {
            throw new AuthenticationException("Invalid refresh token") {
            };
        }
        deleteRefreshToken(username);
        return generateTokens(username);
    }

}
