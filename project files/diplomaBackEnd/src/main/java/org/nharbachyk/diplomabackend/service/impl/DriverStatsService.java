package org.nharbachyk.diplomabackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.nharbachyk.diplomabackend.entities.tripReport.DriverDailyStats;
import org.nharbachyk.diplomabackend.entities.tripReport.DriverEntity;
import org.nharbachyk.diplomabackend.repository.jpa.DriverDailyStatsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DriverStatsService {

    private final DriverDailyStatsRepository statsRepository;

    public List<DriverDailyStats> getDriverStats(Long driverId, LocalDate startDate, LocalDate endDate) {
        return statsRepository.findByDriver_IdAndStartDateBetween(driverId, startDate, endDate);
    }

    public Map<DriverEntity, List<DriverDailyStats>> getDriversComparisonStats(
            List<Long> driverIds,
            LocalDate startDate,
            LocalDate endDate) {
        List<DriverDailyStats> stats = statsRepository.findByDriver_IdInAndStartDateBetween(
                driverIds, startDate, endDate);

        return stats.stream()
                .collect(Collectors.groupingBy(DriverDailyStats::getDriver));
    }
}