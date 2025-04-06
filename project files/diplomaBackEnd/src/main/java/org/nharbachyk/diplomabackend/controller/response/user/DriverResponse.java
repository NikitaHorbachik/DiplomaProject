package org.nharbachyk.diplomabackend.controller.response.user;

public record DriverResponse(
        Long userId,
        String phone,
        String licenseNumber
) {
}
