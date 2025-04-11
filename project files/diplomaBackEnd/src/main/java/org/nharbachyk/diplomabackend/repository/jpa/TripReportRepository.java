package org.nharbachyk.diplomabackend.repository.jpa;

import jakarta.validation.constraints.NotNull;
import org.nharbachyk.diplomabackend.entities.tripReport.DriverEntity;
import org.nharbachyk.diplomabackend.entities.tripReport.TripReportEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TripReportRepository extends JpaRepository<TripReportEntity, Long> {

    <T> List<TripReportEntity> findAllByDriver_IdAndStartDatetimeAfterAndEndDatetimeBefore(T driver_id,
                                                                                           @NotNull LocalDateTime startDatetime,
                                                                                           @NotNull LocalDateTime endDatetime);

    Page<TripReportEntity> findAllByCargoIdAndStartDatetimeAfterAndEndDatetimeBefore(String cargoId,
                                                                                     @NotNull LocalDateTime startDatetime,
                                                                                     @NotNull LocalDateTime endDatetime, Pageable pageable);

    <T> Page<TripReportEntity> findByDriver_Id(T driver_id, Pageable pageable);

    List<TripReportEntity> findAllByDriver(@NotNull DriverEntity driver);

    List<TripReportEntity> findAllByDriverAndEndDatetimeBefore(@NotNull DriverEntity driver, @NotNull LocalDateTime endDatetime);

    List<TripReportEntity> findAllByDriverAndStartDatetimeAfter(@NotNull DriverEntity driver, @NotNull LocalDateTime startDatetime);

    List<TripReportEntity> findAllByDriverAndStartDatetimeAfterAndEndDatetimeBefore(@NotNull DriverEntity driver,
                                                                                    @NotNull LocalDateTime startDatetime,
                                                                                    @NotNull LocalDateTime endDatetime);

}
