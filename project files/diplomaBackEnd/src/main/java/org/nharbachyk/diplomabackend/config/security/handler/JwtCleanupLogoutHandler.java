package org.nharbachyk.diplomabackend.config.security.handler;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.nharbachyk.diplomabackend.service.TokenService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtCleanupLogoutHandler implements LogoutHandler {

    private final TokenService tokenService;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null) {
            authorizationHeader = authorizationHeader.trim();
            if (authorizationHeader.startsWith(TokenService.BEARER_TOKEN)) {
                String accessToken = authorizationHeader.substring(7);
                try {
                    String username = tokenService.extractUsername(accessToken);
                    tokenService.invalidateTokens(username);
                } catch (ExpiredJwtException e) {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                }
            }
        }
    }
}
