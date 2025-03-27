package org.nharbachyk.diplomabackend.controller.request;

import jakarta.validation.constraints.NotBlank;

public record RoleRequest(@NotBlank String name) {
}
