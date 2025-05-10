package org.nharbachyk.diplomabackend.repository.jpa;

import org.nharbachyk.diplomabackend.entities.tripReport.DriverDailyStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DriverDailyStatsRepository extends JpaRepository<DriverDailyStats, Long> {

    // Получение статистики по водителю за период
    List<DriverDailyStats> findByDriver_IdAndStartDateBetween(Long driverId, LocalDate startDate, LocalDate endDate);

    // Получение статистики по нескольким водителям за период
    List<DriverDailyStats> findByDriver_IdInAndStartDateBetween(
            List<Long> drivers,
            LocalDate startDate,
            LocalDate endDate);
}