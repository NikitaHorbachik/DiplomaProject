package org.nharbachyk.diplomabackend.entities.tripReport;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.nharbachyk.diplomabackend.entities.BaseEntity;

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

}