package org.nharbachyk.diplomabackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.nharbachyk.diplomabackend.controller.request.user.*;
import org.nharbachyk.diplomabackend.controller.response.user.UserResponse;
import org.nharbachyk.diplomabackend.entities.user.RoleEntity;
import org.nharbachyk.diplomabackend.entities.user.UserEntity;
import org.nharbachyk.diplomabackend.exceptions.UserNotFoundException;
import org.nharbachyk.diplomabackend.mapper.UserMapper;
import org.nharbachyk.diplomabackend.repository.jpa.RoleRepository;
import org.nharbachyk.diplomabackend.repository.jpa.UserRepository;
import org.nharbachyk.diplomabackend.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Long create(CreateUserRequest createUser) {
        UserEntity userEntity = userMapper.toEntity(createUser);

        String unencryptedPassword = userEntity.getPassword();
        String encryptedPassword = passwordEncoder.encode(unencryptedPassword);
        userEntity.setPassword(encryptedPassword);
        userEntity.setRoles(
                createUser.roles()
                        .stream()
                        .map(roleRequest -> roleRepository.findByAuthority(roleRequest.name()))
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .toList()
        );

        return userRepository.save(userEntity).getId();
    }

    @Transactional
    @Override
    public UserResponse findById(Long id) {
        UserEntity user = getByIdOrThrow(id);
        return userMapper.toResponse(user);
    }

    @Transactional
    @Override
    public UserResponse findByLogin(String username) throws UsernameNotFoundException {
        return userMapper.toResponse(userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with name " + username)));
    }


    @Transactional
    @Override
    public Page<UserResponse> findAll(Pageable pageable) {
        Page<UserEntity> userEntities = userRepository.findAll(pageable);
        return userEntities.map(userMapper::toResponse);
    }

    @Transactional
    @Override
    public UserEntity findByLoginOrThrow(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with name " + username));
    }

    @Override
    public void update(Long id, UpdateUserRequest updateUser) {
        UserEntity updatedUser = getByIdOrThrow(id);
        userMapper.updateEntity(updateUser, updatedUser);
        userRepository.save(updatedUser);
    }

    @Override
    @Transactional
    public void updateEmail(Long id, UpdateUserEmailRequest request, String login) {
        UserEntity user = getByIdOrThrow(id);

        if (!user.getUsername().equals(login)
                && !getByUsernameOrThrow(login).hasRole("ADMIN")) {
            throw new AccessDeniedException("You can only change your own email");
        }

        user.setEmail(request.email());
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updatePassword(Long id, UpdateUserPasswordRequest request, String login) {
        UserEntity user = getByIdOrThrow(id);

        if (!user.getUsername().equals(login)
                && !getByUsernameOrThrow(login).hasRole("ADMIN")) {
            throw new AccessDeniedException("You can only change your own password");
        }
        if (passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new AccessDeniedException("You cannot use your old password");
        }

        user.setPassword(passwordEncoder.encode(request.password()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateLogin(Long id, UpdateUserLoginRequest request, String login) {
        UserEntity user = getByIdOrThrow(id);
        if (!user.getUsername().equals(login)
                && !getByUsernameOrThrow(login).hasRole("ADMIN")) {
            throw new AccessDeniedException("You can only change your own password");
        }

        user.setUsername(request.username());
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void addRole(Long id, Long roleId) {
        UserEntity user = getByIdOrThrow(id);
        RoleEntity roleEntity = getRoleByIdOrThrow(roleId);
        user.getRoles().add(roleEntity);
        userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void removeRole(Long id, Long roleId) {
        UserEntity user = getByIdOrThrow(id);
        RoleEntity roleEntity = getRoleByIdOrThrow(roleId);
        user.getRoles().remove(roleEntity);
        userRepository.save(user);
    }

    private UserEntity getByIdOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    private RoleEntity getRoleByIdOrThrow(Long roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(UserNotFoundException::new);
    }

    private UserEntity getByUsernameOrThrow(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);
    }

}
