package org.nharbachyk.diplomabackend.controller.request.tripReport;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record UpdateTripReportRequest(
        @Size(max = 255)
        String cargoId,

        @Size(max = 1000)
        String endLocation,

        @FutureOrPresent
        LocalDateTime endDatetime,

        @PositiveOrZero
        Double distanceKm,

        @PositiveOrZero
        Long totalFuelConsumed
) {
}
