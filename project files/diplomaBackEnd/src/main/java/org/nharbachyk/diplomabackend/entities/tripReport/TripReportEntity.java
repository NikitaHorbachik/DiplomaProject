package org.nharbachyk.diplomabackend.entities.tripReport;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.nharbachyk.diplomabackend.entities.BaseEntity;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "trip_reports", schema = "diploma")
public class TripReportEntity extends BaseEntity<Long> {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "driver_id", nullable = false)
    private DriverEntity driver;

    @Column(name = "cargo_id", length = Integer.MAX_VALUE)
    private String cargoId;

    @NotNull
    @Column(name = "start_location", nullable = false, length = Integer.MAX_VALUE)
    private String startLocation;

    @NotNull
    @Column(name = "end_location", nullable = false, length = Integer.MAX_VALUE)
    private String endLocation;

    @NotNull
    @Column(name = "start_datetime", nullable = false)
    private LocalDateTime startDatetime;

    @NotNull
    @Column(name = "end_datetime", nullable = false)
    private LocalDateTime endDatetime;

    @Column(name = "total_fuel_consumed")
    private Long totalFuelConsumed;

    @NotNull
    @Column(name = "total_distance", nullable = false)
    @PositiveOrZero(message = "Distance must be positive or zero")
    private Double distanceKm;

    // Дополнительные методы для бизнес-логики

    /**
     * Рассчитывает среднюю скорость в км/ч
     */
    public Double calculateAverageSpeed() {
        if (distanceKm == null || startDatetime == null || endDatetime == null) {
            return null;
        }
        long hours = Duration.between(startDatetime, endDatetime).toHours();
        return hours > 0 ? distanceKm / hours : 0.0;
    }

    /**
     * Рассчитывает расход топлива на 100 км
     */
    public Double calculateFuelConsumptionPer100Km() {
        if (totalFuelConsumed == null || distanceKm == null || distanceKm == 0) {
            return null;
        }
        return (totalFuelConsumed / distanceKm) * 100;
    }
}