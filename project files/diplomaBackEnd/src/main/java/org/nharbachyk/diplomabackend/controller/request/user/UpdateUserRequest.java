package org.nharbachyk.diplomabackend.controller.request.user;

public record UpdateUserRequest(
        String name,
        String surname) {
}
