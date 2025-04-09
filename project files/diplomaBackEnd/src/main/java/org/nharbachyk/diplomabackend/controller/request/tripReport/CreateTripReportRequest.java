package org.nharbachyk.diplomabackend.controller.request.tripReport;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record CreateTripReportRequest(
        @NotNull
        Long driverId,

        @Size
        String cargoId,

        @NotBlank
        @Size(max = 1000, message = "Start location too long")
        String startLocation,

        @NotBlank
        @Size(max = 1000, message = "End location too long")
        String endLocation,

        @NotNull
        @PastOrPresent(message = "Start date must be in past or present")
        LocalDateTime startDatetime,

        @NotNull
        @PastOrPresent(message = "End date must be in past or present")
        LocalDateTime endDatetime,

        @PositiveOrZero
        @NotNull(message = "Distance is required")
        Double distanceKm,

        @PositiveOrZero
        Long totalFuelConsumed
) {
}