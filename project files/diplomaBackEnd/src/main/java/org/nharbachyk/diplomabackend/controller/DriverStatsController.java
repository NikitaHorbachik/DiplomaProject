package org.nharbachyk.diplomabackend.controller;

import lombok.RequiredArgsConstructor;
import org.nharbachyk.diplomabackend.entities.tripReport.DriverDailyStats;
import org.nharbachyk.diplomabackend.entities.tripReport.DriverEntity;
import org.nharbachyk.diplomabackend.service.impl.DriverStatsService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/driver-stats")
@RequiredArgsConstructor
public class DriverStatsController {

    private final DriverStatsService statsService;

    @GetMapping("/{driverId}")
    public ResponseEntity<List<DriverDailyStats>> getDriverStats(
            @PathVariable Long driverId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        return ResponseEntity.ok(statsService.getDriverStats(driverId, startDate, endDate));
    }

    @GetMapping("/compare")
    public ResponseEntity<Map<String, List<DriverDailyStats>>> compareDrivers(
            @RequestParam List<Long> driverIds,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        Map<DriverEntity, List<DriverDailyStats>> stats = statsService.getDriversComparisonStats(
                driverIds, startDate, endDate);

        // Преобразуем в Map с именами водителей для удобства клиента
        Map<String, List<DriverDailyStats>> result = stats.entrySet().stream()
                .collect(Collectors.toMap(
                        e -> e.getKey().getUser().getName(),
                        Map.Entry::getValue
                ));

        return ResponseEntity.ok(result);
    }
}