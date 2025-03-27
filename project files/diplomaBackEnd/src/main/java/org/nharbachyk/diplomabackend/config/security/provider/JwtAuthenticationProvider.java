package org.nharbachyk.diplomabackend.config.security.provider;

import lombok.RequiredArgsConstructor;
import org.nharbachyk.diplomabackend.config.security.authToken.JwtAuthenticationToken;
import org.nharbachyk.diplomabackend.exceptions.InvalidJwtTokenException;
import org.nharbachyk.diplomabackend.service.TokenService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final TokenService tokenService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthenticationToken jwtAuthToken = (JwtAuthenticationToken) authentication;
        String jwt = jwtAuthToken.getToken();
        String username = jwtAuthToken.getPrincipal();

        if (tokenService.validateToken(jwt, username)) {
            return jwtAuthToken;
        } else {
            throw new InvalidJwtTokenException();
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
