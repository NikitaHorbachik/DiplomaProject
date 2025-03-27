package org.nharbachyk.diplomabackend.controller.response;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {
}
