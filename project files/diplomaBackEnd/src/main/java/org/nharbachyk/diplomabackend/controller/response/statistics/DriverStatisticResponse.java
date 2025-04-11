package org.nharbachyk.diplomabackend.controller.response.statistics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DriverStatisticResponse {
    private Long driverId;
    private String driverName;
    private Integer totalTrips;
    private Double totalDistanceKm;
    private Double averageFuelConsumptionPer100Km;
    private Double averageSpeedKmh;
    private Double cargoTransportPercentage;

    public static DriverStatisticResponse empty(Long driverId, String driverName) {
        return builder()
                .driverId(driverId)
                .driverName(driverName)
                .totalTrips(0)
                .totalDistanceKm(0.0)
                .averageFuelConsumptionPer100Km(0.0)
                .averageSpeedKmh(0.0)
                .cargoTransportPercentage(0.0)
                .build();
    }
}