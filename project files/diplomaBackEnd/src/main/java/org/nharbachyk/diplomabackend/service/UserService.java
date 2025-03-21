package org.nharbachyk.diplomabackend.service;

import jakarta.validation.Valid;
import org.nharbachyk.diplomabackend.controller.request.CreateUserRequest;
import org.nharbachyk.diplomabackend.controller.request.UpdateUserRequest;
import org.nharbachyk.diplomabackend.controller.response.UserResponse;
import org.nharbachyk.diplomabackend.entities.UserEntity;
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
