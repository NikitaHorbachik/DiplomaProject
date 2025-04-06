package org.nharbachyk.diplomabackend.controller.request.tripReport;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record CreateTripReportRequest(
        @NotNull(message = "Driver ID is required")
        Long driverId,

        @Size(max = 255, message = "Cargo ID must be less than 255 characters")
        String cargoId,

        @NotBlank(message = "Start location is required")
        @Size(max = 1000, message = "Start location too long")
        String startLocation,

        @NotBlank(message = "End location is required")
        @Size(max = 1000, message = "End location too long")
        String endLocation,

        @NotNull(message = "Start datetime is required")
        @Past(message = "End datetime must be in past")
        LocalDateTime startDatetime,

        @NotNull(message = "End datetime is required")
        @PastOrPresent(message = "Start datetime must be in past or now")
        LocalDateTime endDatetime,

        @PositiveOrZero(message = "Fuel consumption cannot be negative")
        Long totalFuelConsumed
) {
}
