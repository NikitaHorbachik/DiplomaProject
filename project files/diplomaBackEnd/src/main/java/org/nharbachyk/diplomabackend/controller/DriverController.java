package org.nharbachyk.diplomabackend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.nharbachyk.diplomabackend.controller.request.user.CreateDriverRequest;
import org.nharbachyk.diplomabackend.service.DriverService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/drivers")
@RequiredArgsConstructor
public class DriverController {

    private final DriverService driverService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long createDriver(
            @RequestBody @Valid CreateDriverRequest request) {
        return driverService.createDriver(request);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> removeDriverRole(@PathVariable Long userId) {
        driverService.removeDriverRole(userId);
        return ResponseEntity.noContent().build();
    }
}