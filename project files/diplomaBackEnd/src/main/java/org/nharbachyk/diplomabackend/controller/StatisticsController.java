package org.nharbachyk.diplomabackend.controller;

import lombok.RequiredArgsConstructor;
import org.nharbachyk.diplomabackend.controller.response.statistics.DriverStatisticResponse;
import org.nharbachyk.diplomabackend.service.StatisticsService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("api/v1/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticService;

    @GetMapping("/driver/{driverId}")
    public DriverStatisticResponse getDriverStatistics(
            @PathVariable Long driverId,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        return statisticService.getDriverStatistics(driverId, startDate, endDate);
    }
}