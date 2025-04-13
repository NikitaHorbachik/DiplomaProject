package org.nharbachyk.diplomabackend.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.nharbachyk.diplomabackend.controller.request.organization.CreateOrganizationRequest;
import org.nharbachyk.diplomabackend.controller.request.organization.UpdateOrganizationRequest;
import org.nharbachyk.diplomabackend.controller.response.organization.OrganizationResponse;
import org.nharbachyk.diplomabackend.entities.organization.OrganizationEntity;
import org.nharbachyk.diplomabackend.entities.tripReport.DriverEntity;
import org.nharbachyk.diplomabackend.mapper.OrganizationMapper;
import org.nharbachyk.diplomabackend.repository.jpa.OrganizationRepository;
import org.nharbachyk.diplomabackend.service.DriverService;
import org.nharbachyk.diplomabackend.service.OrganizationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final OrganizationMapper organizationMapper;
    private final DriverService driverService;

    @Override
    public Long create(CreateOrganizationRequest request) {
        OrganizationEntity entity = organizationMapper.toEntity(request);
        return organizationRepository.save(entity).getId();
    }

    @Override
    public Page<OrganizationResponse> findAll(Pageable pageable) {
        return organizationRepository.findAll(pageable)
                .map(organizationMapper::toResponse);
    }

    @Override
    public OrganizationResponse findById(Long id) {
        return organizationMapper.toResponse(getByIdOrThrow(id));
    }

    @Override
    public void update(Long id, UpdateOrganizationRequest request) {
        OrganizationEntity entity = getByIdOrThrow(id);
        entity.setName(request.name());
        organizationRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        organizationRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void addDriver(Long id, Long driverId) {
        OrganizationEntity organization = getByIdOrThrow(id);
        DriverEntity driver = driverService.getByIdOrThrow(driverId);
        driver.getUser().setOrganization(organization);
    }

    @Transactional
    @Override
    public void removeDriver(Long id, Long driverId) {
        DriverEntity driver = driverService.getByIdOrThrow(driverId);
        driver.getUser().setOrganization(null);
    }

    private OrganizationEntity getByIdOrThrow(Long id) {
        return organizationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Organization not found"));
    }
}
