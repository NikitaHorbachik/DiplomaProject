package org.nharbachyk.diplomabackend.config.security.filter;

import io.micrometer.common.lang.NonNullApi;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.nharbachyk.diplomabackend.config.security.authToken.JwtAuthenticationToken;
import org.nharbachyk.diplomabackend.controller.response.TokenResponse;
import org.nharbachyk.diplomabackend.utils.JwtUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@NonNullApi
@RequiredArgsConstructor
public class RefreshJwtFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    private final String AUTH_PATH = "/api/v1/refresh";
    private final RequestMatcher requestMatcher = new AntPathRequestMatcher(AUTH_PATH);
    private final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (requestMatcher.matches(request)) {
            filterRefreshToken(request, response);
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private void filterRefreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null) {
            authorizationHeader = authorizationHeader.trim();
            if (authorizationHeader.startsWith(JwtUtils.BEARER_TOKEN)) {
                String refreshToken = authorizationHeader.substring(7);
                String username = jwtUtils.extractUsername(refreshToken);
                JwtAuthenticationToken jwtAuthToken = new JwtAuthenticationToken(username, refreshToken);
                Authentication authentication = authenticationManager.authenticate(jwtAuthToken);
                OnSuccessfulRefresh(response, authentication);
            }
        }
    }

    private void OnSuccessfulRefresh(HttpServletResponse response, Authentication authResult) throws IOException {
        SecurityContextHolder.getContext().setAuthentication(authResult);
        JwtAuthenticationToken jwtAuthToken = (JwtAuthenticationToken) authResult;
        String token = jwtAuthToken.getToken();
        String principal = (String) authResult.getPrincipal();
        List<String> tokens = jwtUtils.refreshTokens(principal, token);
        TokenResponse tokenResponse = new TokenResponse(tokens);
        converter.write(tokenResponse, MediaType.APPLICATION_JSON, new ServletServerHttpResponse(response));
    }

}

