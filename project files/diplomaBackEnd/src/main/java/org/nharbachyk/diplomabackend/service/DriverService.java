package org.nharbachyk.diplomabackend.service;

import org.nharbachyk.diplomabackend.controller.request.user.CreateDriverRequest;
import org.nharbachyk.diplomabackend.entities.tripReport.DriverEntity;
import org.springframework.transaction.annotation.Transactional;

public interface DriverService {
    @Transactional
    Long createDriver(CreateDriverRequest request);

    Long findDriverIdByUserId(Long id);

    DriverEntity getByIdOrThrow(Long id);

    @Transactional
    void removeDriverRole(Long userId);
}
