package org.nharbachyk.diplomabackend.repository.jpa;

import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.NotNull;
import org.nharbachyk.diplomabackend.entities.tripReport.DriverEntity;
import org.nharbachyk.diplomabackend.entities.tripReport.TripReportEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    @Query("""
                 SELECT t FROM TripReportEntity t\s
                 WHERE t.driver = :driver
                   AND t.startDatetime >= :startDatetime\s
                   AND t.endDatetime <= :endDatetime
            \s""")
    List<TripReportEntity> findAllByDriverAndOptionalPeriod(
            @NotNull @Param("driver") DriverEntity driver,
            @Nullable @Param("startDatetime") LocalDateTime startDatetime,
            @Nullable @Param("endDatetime") LocalDateTime endDatetime
    );

    List<TripReportEntity> findAllByDriverAndStartDatetimeAfterAndEndDatetimeBefore(
            @NotNull DriverEntity driver,
            @NotNull LocalDateTime startDatetime,
            @NotNull LocalDateTime endDatetime);

    @Query("""
                SELECT t FROM TripReportEntity t 
                WHERE t.driver IN :drivers 
                  AND t.startDatetime >= :startDate 
                  AND t.endDatetime <= :endDate
            """)
    List<TripReportEntity> findAllByDriverInAndDateRange(@Param("drivers") List<DriverEntity> drivers,
                                                         @Param("startDate") LocalDateTime startDate,
                                                         @Param("endDate") LocalDateTime endDate
    );
}
