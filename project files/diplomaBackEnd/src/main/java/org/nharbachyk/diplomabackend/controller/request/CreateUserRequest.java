package org.nharbachyk.diplomabackend.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateUserRequest(
        @NotBlank String username,
        @Email String email,
        @NotBlank String name,
        String surname,
        @NotBlank String password,
        @NotNull List<RoleRequest> roles
) {
}
