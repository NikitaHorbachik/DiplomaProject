package org.nharbachyk.diplomabackend.service;

import jakarta.validation.Valid;
import org.nharbachyk.diplomabackend.controller.request.user.CreateUserRequest;
import org.nharbachyk.diplomabackend.controller.request.user.UpdateUserRequest;
import org.nharbachyk.diplomabackend.controller.response.user.UserResponse;
import org.nharbachyk.diplomabackend.entities.user.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {

    Long create(CreateUserRequest createUserRequest);

    @Transactional
    UserResponse findById(Long id);

    @Transactional
    Page<UserResponse> findAll(Pageable pageable);

    @Transactional
    UserEntity findByLoginOrThrow(String username) throws UsernameNotFoundException;

    void update(Long id, @Valid UpdateUserRequest updateUser);

    @Transactional
    void addRole(Long id, Long roleId);

    void delete(Long id);

    @Transactional
    void removeRole(Long id, Long roleId);
}
