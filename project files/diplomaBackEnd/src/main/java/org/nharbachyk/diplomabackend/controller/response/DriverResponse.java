package org.nharbachyk.diplomabackend.controller.response;

public record DriverResponse(
        Long userId,
        String phone,
        String licenseNumber
) {
}
