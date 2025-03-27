package org.nharbachyk.diplomabackend.controller.request;

public record UpdateUserRequest(
        String name,
        String surname) {
}
