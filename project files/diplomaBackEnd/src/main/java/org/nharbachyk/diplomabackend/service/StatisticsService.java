package org.nharbachyk.diplomabackend.service;

import org.nharbachyk.diplomabackend.controller.response.organization.OrganizationStatisticResponse;
import org.nharbachyk.diplomabackend.controller.response.statistics.DriverStatisticResponse;

import java.time.LocalDate;

public interface StatisticsService {

    DriverStatisticResponse getDriverStatistics(Long driverId,
                                                LocalDate startDate,
                                                LocalDate endDate);

    OrganizationStatisticResponse getOrganizationStatistics(Long organizationId,
                                                            LocalDate startDate,
                                                            LocalDate endDate);

}
