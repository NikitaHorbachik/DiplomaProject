package org.nharbachyk.diplomabackend.mapper;

import org.nharbachyk.diplomabackend.controller.request.organization.CreateOrganizationRequest;
import org.nharbachyk.diplomabackend.controller.request.organization.UpdateOrganizationRequest;
import org.nharbachyk.diplomabackend.controller.response.organization.OrganizationResponse;
import org.nharbachyk.diplomabackend.entities.organization.OrganizationEntity;
import org.springframework.stereotype.Component;

@Component
public class OrganizationMapper {

    public OrganizationEntity toEntity(CreateOrganizationRequest request) {
        OrganizationEntity entity = new OrganizationEntity();
        entity.setName(request.name());
        return entity;
    }

    public void updateEntity(UpdateOrganizationRequest request, OrganizationEntity entity) {
        entity.setName(request.name());
    }

    public OrganizationResponse toResponse(OrganizationEntity entity) {
        return new OrganizationResponse(
                entity.getId(),
                entity.getName()
        );
    }
}
