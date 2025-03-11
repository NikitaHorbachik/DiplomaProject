package org.nharbachyk.diplomabackend.controller.request;

public record UpdateUserRequest(
        String email,
        String name,
        String surname) {
}
