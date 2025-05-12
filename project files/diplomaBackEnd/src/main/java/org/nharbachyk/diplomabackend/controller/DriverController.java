package org.nharbachyk.diplomabackend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.nharbachyk.diplomabackend.controller.request.user.CreateDriverRequest;
import org.nharbachyk.diplomabackend.controller.response.IdResponse;
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
    public IdResponse createDriver(
            @RequestBody @Valid CreateDriverRequest request) {
        return new IdResponse(driverService.createDriver(request));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{userId}")
    public IdResponse findByUserId(@PathVariable Long userId) {

        return new IdResponse(driverService.findDriverIdByUserId(userId));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> removeDriverRole(@PathVariable Long userId) {
        driverService.removeDriverRole(userId);
        return ResponseEntity.noContent().build();
    }

}