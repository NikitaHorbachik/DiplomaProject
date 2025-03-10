package org.nharbachyk.diplomabackend.service;

import lombok.RequiredArgsConstructor;
import org.nharbachyk.diplomabackend.controller.request.CreateUserRequest;
import org.nharbachyk.diplomabackend.controller.response.UserResponse;
import org.nharbachyk.diplomabackend.entities.UserEntity;
import org.nharbachyk.diplomabackend.mapper.UserMapper;
import org.nharbachyk.diplomabackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public Long create(CreateUserRequest createUserRequest) {
        UserEntity newUser = userMapper.toEntity(createUserRequest);
        return userRepository.save(newUser).getId();
    }

    @Override
    public List<UserResponse> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserResponse)
                .toList();
    }

}
