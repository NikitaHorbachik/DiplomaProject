package org.nharbachyk.diplomabackend.controller.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateUserEmailRequest(@NotBlank @Email String email) {
}
