package org.nharbachyk.diplomabackend.repository.jpa;

import jakarta.validation.constraints.NotNull;
import org.nharbachyk.diplomabackend.entities.tripReport.TripReportEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TripReportRepository extends JpaRepository<TripReportEntity, Long> {

    <T> List<TripReportEntity> findAllByDriver_IdAndStartDatetimeAfterAndEndDatetimeBefore(@NotNull T driverId,
                                                                                           @NotNull LocalDateTime startDatetime,
                                                                                           @NotNull LocalDateTime endDatetime);

    Page<TripReportEntity> findAllByCargoIdAndStartDatetimeAfterAndEndDatetimeBefore(String cargoId,
                                                                                     @NotNull LocalDateTime startDatetime,
                                                                                     @NotNull LocalDateTime endDatetime, Pageable pageable);

    <T> Page<TripReportEntity> findByDriver_Id(T driverId, Pageable pageable);


}
