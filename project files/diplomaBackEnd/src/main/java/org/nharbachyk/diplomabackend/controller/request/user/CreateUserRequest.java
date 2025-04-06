package org.nharbachyk.diplomabackend.controller.request.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.nharbachyk.diplomabackend.controller.request.RoleRequest;

import java.util.List;

public record CreateUserRequest(
        @NotBlank String username,
        @NotBlank @Email String email,
        @NotBlank String name,
        String surname,
        @NotBlank @Size(min = 8) String password,
        @NotNull List<@Valid RoleRequest> roles
) {
}
