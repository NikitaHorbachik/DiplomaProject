package org.nharbachyk.diplomabackend.controller.response.tripReport;

import java.time.LocalDateTime;

public record TripReportResponse(
        Long id,
        Long driverId,
        String driverName,
        String cargoId,
        String startLocation,
        String endLocation,
        Double distanceKm,
        LocalDateTime startDatetime,
        LocalDateTime endDatetime,
        Double averageSpeedKmh,
        Long totalFuelConsumed
) {
}