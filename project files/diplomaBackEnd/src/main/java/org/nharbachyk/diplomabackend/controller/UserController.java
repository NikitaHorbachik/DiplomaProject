package org.nharbachyk.diplomabackend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.nharbachyk.diplomabackend.controller.request.CreateUserRequest;
import org.nharbachyk.diplomabackend.controller.request.UpdateUserRequest;
import org.nharbachyk.diplomabackend.controller.response.UserResponse;
import org.nharbachyk.diplomabackend.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public Long create(@RequestBody CreateUserRequest createUserRequest) {
        return userService.create(createUserRequest);
    }

    @GetMapping
    public Page<UserResponse> findAll(Pageable pageable) {
        return userService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public UserResponse findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Long id, @RequestBody @Valid UpdateUserRequest updateUser) {
        userService.update(id, updateUser);
    }

    @PatchMapping("/{id}/roles")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addRole(@PathVariable Long id, @RequestBody Long roleId) {
        userService.addRole(id, roleId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @DeleteMapping("/{id}/roles")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeRole(@PathVariable Long id, @RequestBody Long roleId) {
        userService.removeRole(id, roleId);
    }
}
