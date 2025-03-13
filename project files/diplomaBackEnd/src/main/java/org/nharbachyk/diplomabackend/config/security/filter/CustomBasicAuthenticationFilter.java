package org.nharbachyk.diplomabackend.config.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.nharbachyk.diplomabackend.controller.response.TokenResponse;
import org.nharbachyk.diplomabackend.utils.JwtUtils;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class CustomBasicAuthenticationFilter extends BasicAuthenticationFilter {

    private final JwtUtils jwtUtils;

    private final String AUTH_PATH = "/api/v1/auth";
    private final RequestMatcher requestMatcher = new AntPathRequestMatcher(AUTH_PATH);
    private final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

    public CustomBasicAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        super(authenticationManager);
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (requestMatcher.matches(request)) {
            super.doFilterInternal(request, response, filterChain);
        } else {
            filterChain.doFilter(request, response);
        }
    }

    @Override
    protected void onSuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              Authentication authResult) throws IOException {
        SecurityContextHolder.getContext().setAuthentication(authResult);
        UserDetails principal = (UserDetails) authResult.getPrincipal();
        List<String> tokens = jwtUtils.generateTokens(principal.getUsername());
        TokenResponse tokenResponse = new TokenResponse(tokens);
        converter.write(tokenResponse, MediaType.APPLICATION_JSON, new ServletServerHttpResponse(response));
    }

}
