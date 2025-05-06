package org.nharbachyk.diplomabackend.entities.tripReport;

import jakarta.persistence.*;
import lombok.*;
import org.nharbachyk.diplomabackend.entities.BaseEntity;

import java.time.LocalDate;

@Entity
@Table(name = "driver_daily_stats", schema = "diploma")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverDailyStats extends BaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", nullable = false)
    private DriverEntity driver;

    @Column(name = "stat_date", nullable = false)
    private LocalDate statDate;

    @Column(name = "total_distance", nullable = false)
    private Double totalDistance;

    @Column(name = "total_fuel", nullable = false)
    private Long totalFuel;

    @Column(name = "total_trips", nullable = false)
    private Integer totalTrips;

    @Column(name = "avg_fuel_per_km")
    private Double avgFuelPerKm;

    public void addTripStats(Double distance, Long fuel) {
        this.totalDistance += distance;
        this.totalFuel += fuel;
        this.totalTrips += 1;
        recalculateAvgFuel();
    }

    public void removeTripStats(Double distance, Long fuel) {
        this.totalDistance -= distance;
        this.totalFuel -= fuel;
        this.totalTrips -= 1;
        recalculateAvgFuel();
    }

    private void recalculateAvgFuel() {
        this.avgFuelPerKm = (totalDistance > 0 && totalFuel > 0)
                ? totalFuel / totalDistance
                : null;
    }

}