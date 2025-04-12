package org.nharbachyk.diplomabackend.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.nharbachyk.diplomabackend.controller.response.organization.OrganizationStatisticResponse;
import org.nharbachyk.diplomabackend.controller.response.statistics.DriverStatisticResponse;
import org.nharbachyk.diplomabackend.entities.tripReport.DriverEntity;
import org.nharbachyk.diplomabackend.entities.tripReport.TripReportEntity;
import org.nharbachyk.diplomabackend.repository.jpa.DriverRepository;
import org.nharbachyk.diplomabackend.repository.jpa.TripReportRepository;
import org.nharbachyk.diplomabackend.service.StatisticsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatisticsServiceImpl implements StatisticsService {

    private final TripReportRepository tripReportRepository;
    private final DriverRepository driverRepository;

    @Override
    public DriverStatisticResponse getDriverStatistics(Long driverId, LocalDate startDate, LocalDate endDate) {
        DriverEntity driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new EntityNotFoundException("Driver not found"));

        List<TripReportEntity> trips;
        if (startDate == null && endDate == null) {
            trips = tripReportRepository.findAllByDriver(driver);
        } else if (startDate == null) {
            trips = tripReportRepository.findAllByDriverAndEndDatetimeBefore(driver, endDate.atStartOfDay());
        } else if (endDate == null) {
            trips = tripReportRepository.findAllByDriverAndStartDatetimeAfter(driver, startDate.atStartOfDay());
        } else {
            trips = tripReportRepository.findAllByDriverAndStartDatetimeAfterAndEndDatetimeBefore(
                    driver, startDate.atStartOfDay(), endDate.atStartOfDay());
        }

        return calculateDriverStatistics(driver, trips);
    }

    @Override
    public OrganizationStatisticResponse getOrganizationStatistics(Long organizationId, LocalDate startDate, LocalDate endDate) {
        List<DriverEntity> drivers = driverRepository.findAllByUser_Organization_Id(organizationId);
        if (drivers.isEmpty()) {
            throw new EntityNotFoundException("No drivers found for organization");
        }

        List<TripReportEntity> trips = tripReportRepository.findAllByDriverInAndDateRange(drivers,
                startDate.atStartOfDay(),
                endDate.atStartOfDay());

        double totalDistance = trips.stream()
                .mapToDouble(TripReportEntity::getDistanceKm)
                .sum();

        long totalFuel = trips.stream()
                .mapToLong(t -> t.getTotalFuelConsumed() != null ? t.getTotalFuelConsumed() : 0)
                .sum();

        return OrganizationStatisticResponse.builder()
                .organizationId(organizationId)
                .totalDrivers(drivers.size())
                .totalTrips(trips.size())
                .totalDistanceKm(totalDistance)
                .totalFuelConsumed(totalFuel)
                .averageFuelConsumptionPer100Km(calculateAvgFuelConsumption(totalFuel, totalDistance))
                .averageSpeedKmh(calculateAvgSpeed(trips))
                .cargoTransportPercentage(calculateCargoPercentage(trips))
                .build();
    }

    private DriverStatisticResponse calculateDriverStatistics(DriverEntity driver, List<TripReportEntity> trips) {
        if (trips.isEmpty()) {
            return DriverStatisticResponse.empty(driver.getId(), driver.getUser().getName());
        }

        double totalDistance = trips.stream()
                .mapToDouble(TripReportEntity::getDistanceKm)
                .sum();

        long totalFuel = trips.stream()
                .mapToLong(t -> t.getTotalFuelConsumed() != null ? t.getTotalFuelConsumed() : 0)
                .sum();

        return DriverStatisticResponse.builder()
                .driverId(driver.getId())
                .driverName(driver.getUser().getName())
                .totalTrips(trips.size())
                .totalDistanceKm(totalDistance)
                .averageFuelConsumptionPer100Km(calculateAvgFuelConsumption(totalFuel, totalDistance))
                .averageSpeedKmh(calculateAvgSpeed(trips))
                .cargoTransportPercentage(calculateCargoPercentage(trips))
                .build();
    }

    // Методы расчета (остаются без изменений)
    private double calculateAvgFuelConsumption(long totalFuel, double totalDistance) {
        return totalDistance > 0 ? (totalFuel / totalDistance) * 100 : 0;
    }

    private double calculateAvgSpeed(List<TripReportEntity> trips) {
        return trips.stream()
                .mapToDouble(t -> {
                    Duration duration = Duration.between(t.getStartDatetime(), t.getEndDatetime());
                    return duration.toHours() > 0 ? t.getDistanceKm() / duration.toHours() : 0;
                })
                .average()
                .orElse(0);
    }

    private double calculateCargoPercentage(List<TripReportEntity> trips) {
        long cargoTrips = trips.stream()
                .filter(t -> t.getCargoId() != null && !t.getCargoId().isEmpty())
                .count();
        return (double) cargoTrips / trips.size() * 100;
    }
}