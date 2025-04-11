package org.nharbachyk.diplomabackend.service;

import org.nharbachyk.diplomabackend.controller.response.statistics.DriverStatisticResponse;

import java.time.LocalDate;

public interface StatisticsService {
    DriverStatisticResponse getDriverStatistics(Long driverId, LocalDate startDate, LocalDate endDate);
}
