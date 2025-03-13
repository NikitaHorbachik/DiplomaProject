package org.nharbachyk.diplomabackend.config.security.provider;


import lombok.RequiredArgsConstructor;
import org.nharbachyk.diplomabackend.config.security.authToken.JwtAuthenticationToken;
import org.nharbachyk.diplomabackend.exceptions.InvalidJwtTokenException;
import org.nharbachyk.diplomabackend.utils.JwtUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final JwtUtils jwtUtils;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthenticationToken jwtAuthToken = (JwtAuthenticationToken) authentication;
        String jwt = jwtAuthToken.getToken();
        String username = jwtAuthToken.getPrincipal();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (jwtUtils.validateToken(jwt, userDetails)) {
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
