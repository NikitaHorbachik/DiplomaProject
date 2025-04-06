package org.nharbachyk.diplomabackend.controller.request.user;

import jakarta.validation.constraints.NotBlank;

public record RoleRequest(@NotBlank String name) {
}
