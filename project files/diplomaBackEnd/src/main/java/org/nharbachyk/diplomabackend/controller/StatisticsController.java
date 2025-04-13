package org.nharbachyk.diplomabackend.controller;

import lombok.RequiredArgsConstructor;
import org.nharbachyk.diplomabackend.controller.response.organization.OrganizationStatisticResponse;
import org.nharbachyk.diplomabackend.controller.response.statistics.DriverStatisticResponse;
import org.nharbachyk.diplomabackend.service.StatisticsService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("api/v1/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/driver/{driverId}")
    public DriverStatisticResponse getDriverStatistics(
            @PathVariable Long driverId,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        return statisticsService.getDriverStatistics(driverId, startDate, endDate);
    }

    @GetMapping("/organization/{organizationId}")
    public OrganizationStatisticResponse getOrganizationStats(
            @PathVariable Long organizationId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return statisticsService.getOrganizationStatistics(organizationId, startDate, endDate);
    }

}