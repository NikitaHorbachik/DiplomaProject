package org.nharbachyk.diplomabackend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.nharbachyk.diplomabackend.controller.request.organization.CreateOrganizationRequest;
import org.nharbachyk.diplomabackend.controller.request.organization.UpdateOrganizationRequest;
import org.nharbachyk.diplomabackend.controller.response.organization.OrganizationResponse;
import org.nharbachyk.diplomabackend.service.OrganizationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/organizations")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@RequestBody @Valid CreateOrganizationRequest request) {
        return organizationService.create(request);
    }

    @GetMapping
    public Page<OrganizationResponse> findAll(Pageable pageable) {
        return organizationService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public OrganizationResponse findById(@PathVariable Long id) {
        return organizationService.findById(id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Long id, @RequestBody @Valid UpdateOrganizationRequest request) {
        organizationService.update(id, request);
    }

    @PatchMapping("/{id}/drivers/{driverId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addDriver(@PathVariable Long id, @PathVariable Long driverId) {
        organizationService.addDriver(id, driverId);
    }

    @DeleteMapping("/{id}/drivers/{driverId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeDriver(@PathVariable Long id, @PathVariable Long driverId) {
        organizationService.removeDriver(id, driverId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        organizationService.delete(id);
    }
}
