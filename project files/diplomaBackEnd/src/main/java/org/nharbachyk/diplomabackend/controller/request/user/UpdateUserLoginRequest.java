package org.nharbachyk.diplomabackend.controller.request.user;

import jakarta.validation.constraints.NotBlank;

public record UpdateUserLoginRequest(@NotBlank String username) {
}
