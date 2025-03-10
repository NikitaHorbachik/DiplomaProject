package org.nharbachyk.diplomabackend.controller.response;

import java.util.List;

public record UserResponse(
        Long id,
        String username,
        String email,
        String name,
        String surname,
        List<String> roles
) {
}
