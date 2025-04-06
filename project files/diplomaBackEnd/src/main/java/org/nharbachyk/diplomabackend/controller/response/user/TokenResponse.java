package org.nharbachyk.diplomabackend.controller.response.user;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {
}
