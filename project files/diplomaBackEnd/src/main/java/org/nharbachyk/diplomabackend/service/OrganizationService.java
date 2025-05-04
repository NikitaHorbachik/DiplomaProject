package org.nharbachyk.diplomabackend.service;

import org.nharbachyk.diplomabackend.controller.request.organization.CreateOrganizationRequest;
import org.nharbachyk.diplomabackend.controller.request.organization.UpdateOrganizationRequest;
import org.nharbachyk.diplomabackend.controller.response.organization.OrganizationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrganizationService {
    Long create(CreateOrganizationRequest request);
    Page<OrganizationResponse> findAll(Pageable pageable);
    OrganizationResponse findById(Long id);
    void update(Long id, UpdateOrganizationRequest request);
    void delete(Long id);

    void addDriver(Long id, Long driverId);

    void removeDriver(Long id, Long driverId);
}
