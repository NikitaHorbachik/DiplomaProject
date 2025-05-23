package org.nharbachyk.diplomabackend.mapper;

import lombok.RequiredArgsConstructor;
import org.nharbachyk.diplomabackend.controller.request.user.CreateUserRequest;
import org.nharbachyk.diplomabackend.controller.request.user.UpdateUserRequest;
import org.nharbachyk.diplomabackend.controller.response.user.UserResponse;
import org.nharbachyk.diplomabackend.entities.user.RoleEntity;
import org.nharbachyk.diplomabackend.entities.user.UserEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final RoleMapper roleMapper;

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
                user.getFullName(),
                user.getRoles()
                        .stream()
                        .map(RoleEntity::getAuthority)
                        .toList()
        );
    }

    public void updateEntity(UpdateUserRequest updateUser, UserEntity updatedUser) {
        updatedUser.setName(updateUser.name());
        updatedUser.setSurname(updateUser.surname());
    }

    public UserDetails toDetails(UserEntity user) {
        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(roleMapper.toGrantedAuthorityList(user.getRoles()))
                .build();
    }

}