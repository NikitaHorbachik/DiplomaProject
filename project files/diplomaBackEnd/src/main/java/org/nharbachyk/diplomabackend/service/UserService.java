package org.nharbachyk.diplomabackend.service;

import org.nharbachyk.diplomabackend.controller.request.CreateUserRequest;
import org.nharbachyk.diplomabackend.controller.response.UserResponse;

import java.util.List;

public interface UserService {

    Long create(CreateUserRequest createUserRequest);

    List<UserResponse> findAll();

}
