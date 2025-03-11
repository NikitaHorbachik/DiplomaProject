package org.nharbachyk.diplomabackend.mapper;

import lombok.RequiredArgsConstructor;
import org.nharbachyk.diplomabackend.controller.request.CreateUserRequest;
import org.nharbachyk.diplomabackend.controller.request.UpdateUserRequest;
import org.nharbachyk.diplomabackend.controller.response.UserResponse;
import org.nharbachyk.diplomabackend.entities.RoleEntity;
import org.nharbachyk.diplomabackend.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    public UserEntity toEntity(CreateUserRequest createUserRequest) {
        return UserEntity.builder()
                .username(createUserRequest.username())
                .email(createUserRequest.email())
                .name(createUserRequest.name())
                .surname(createUserRequest.surname())
                .password(createUserRequest.password())
                .build();
    }

    public UserResponse toResponse(UserEntity user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getName(),
                user.getSurname(),
                user.getRoles()
                        .stream()
                        .map(RoleEntity::getAuthority)
                        .toList()
        );
    }

    public void updateEntity(UpdateUserRequest updateUser, UserEntity updatedUser) {
        updatedUser.setEmail(updateUser.email());
        updatedUser.setName(updateUser.name());
        updatedUser.setSurname(updateUser.surname());
    }

}