package org.nharbachyk.diplomabackend.config.security.filter;

import io.jsonwebtoken.ExpiredJwtException;
import io.micrometer.common.lang.NonNullApi;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.nharbachyk.diplomabackend.config.security.authToken.JwtAuthenticationToken;
import org.nharbachyk.diplomabackend.service.TokenService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@NonNullApi
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null) {
            authorizationHeader = authorizationHeader.trim();
            if (authorizationHeader.startsWith(TokenService.BEARER_TOKEN)) {
                String accessToken = authorizationHeader.substring(7);
                try {
                    String username = tokenService.extractUsername(accessToken);
                    JwtAuthenticationToken jwtAuthToken = new JwtAuthenticationToken(username, accessToken);
                    Authentication authentication = authenticationManager.authenticate(jwtAuthToken);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } catch (ExpiredJwtException e) {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.getWriter().write("Token expired");
                    return;
                }
            }
        }
        filterChain.doFilter(request, response);
    }

}