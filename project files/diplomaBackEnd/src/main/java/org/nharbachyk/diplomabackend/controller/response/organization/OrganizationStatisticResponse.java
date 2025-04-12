package org.nharbachyk.diplomabackend.controller.response.organization;

import lombok.Builder;

@Builder
public record OrganizationStatisticResponse(
        Long organizationId,
        int totalDrivers,
        int totalTrips,
        double totalDistanceKm,
        long totalFuelConsumed,
        double averageFuelConsumptionPer100Km,
        double averageSpeedKmh,
        double cargoTransportPercentage
) {
}
