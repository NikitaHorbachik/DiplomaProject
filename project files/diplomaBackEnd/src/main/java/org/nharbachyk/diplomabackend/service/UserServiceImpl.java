package org.nharbachyk.diplomabackend.service;

import lombok.RequiredArgsConstructor;
import org.nharbachyk.diplomabackend.controller.request.CreateUserRequest;
import org.nharbachyk.diplomabackend.controller.request.UpdateUserRequest;
import org.nharbachyk.diplomabackend.controller.response.UserResponse;
import org.nharbachyk.diplomabackend.entities.RoleEntity;
import org.nharbachyk.diplomabackend.entities.UserEntity;
import org.nharbachyk.diplomabackend.exceptions.UserNotFoundException;
import org.nharbachyk.diplomabackend.mapper.UserMapper;
import org.nharbachyk.diplomabackend.repository.RoleRepository;
import org.nharbachyk.diplomabackend.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Override
    public Long create(CreateUserRequest createUser) {
        UserEntity userEntity = userMapper.toEntity(createUser);

        //String unencryptedPassword = userEntity.getPassword();
        //String encryptedPassword = passwordEncoder.encode(unencryptedPassword);
        //userEntity.setPassword(encryptedPassword);

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
    public Page<UserResponse> findAll(Pageable pageable) {
        Page<UserEntity> userEntities = userRepository.findAll(pageable);
        return userEntities.map(userMapper::toResponse);
    }

    @Transactional
    @Override
    public UserEntity findByLoginOrThrow(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with username " + username));
    }

    @Override
    public void update(Long id, UpdateUserRequest updateUser) {
        UserEntity updatedUser = getByIdOrThrow(id);
        userMapper.updateEntity(updateUser, updatedUser);
        userRepository.save(updatedUser);
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

}
