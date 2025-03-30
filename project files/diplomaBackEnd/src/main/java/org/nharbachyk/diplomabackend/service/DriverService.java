package org.nharbachyk.diplomabackend.service;

import org.nharbachyk.diplomabackend.controller.request.CreateDriverRequest;
import org.springframework.transaction.annotation.Transactional;

public interface DriverService {
    @Transactional
    Long createDriver(CreateDriverRequest request);

    @Transactional
    void removeDriverRole(Long userId);
}
