package org.nharbachyk.diplomabackend.exceptions;

import org.springframework.security.core.AuthenticationException;

public class InvalidJwtTokenException extends AuthenticationException {
    public InvalidJwtTokenException() {
        super("Token is invalid");
    }
}
