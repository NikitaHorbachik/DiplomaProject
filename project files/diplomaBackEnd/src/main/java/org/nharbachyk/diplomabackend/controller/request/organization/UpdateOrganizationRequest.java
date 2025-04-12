package org.nharbachyk.diplomabackend.controller.request.organization;

import jakarta.validation.constraints.NotBlank;

public record UpdateOrganizationRequest(@NotBlank String name) {
}
