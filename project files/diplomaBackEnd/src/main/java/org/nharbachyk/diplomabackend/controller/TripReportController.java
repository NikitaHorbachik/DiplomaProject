package org.nharbachyk.diplomabackend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.nharbachyk.diplomabackend.controller.request.tripReport.CreateTripReportRequest;
import org.nharbachyk.diplomabackend.controller.request.tripReport.UpdateTripReportRequest;
import org.nharbachyk.diplomabackend.controller.response.tripReport.TripReportResponse;
import org.nharbachyk.diplomabackend.service.TripReportService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/v1/trip-reports")
@RequiredArgsConstructor
public class TripReportController {

    private final TripReportService tripReportService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@RequestBody @Valid CreateTripReportRequest request) {
        return tripReportService.create(request);
    }

    @GetMapping
    public Page<TripReportResponse> findAll(Pageable pageable) {
        return tripReportService.findAll(pageable);
    }

    @GetMapping("/driver/{driverId}")
    public Page<TripReportResponse> findByDriver(
            @PathVariable Long driverId,
            Pageable pageable) {
        return tripReportService.findByDriver(driverId, pageable);
    }

    @GetMapping("/cargo")
    public Page<TripReportResponse> findAllByCargoId(
            @RequestParam(required = false) String cargoId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime endDate,
            Pageable pageable) {
        return tripReportService.findAllByCargoId(cargoId, startDate, endDate, pageable);
    }

    @GetMapping("/{id}")
    public TripReportResponse findById(@PathVariable Long id) {
        return tripReportService.findById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(
            @PathVariable Long id,
            @RequestBody @Valid UpdateTripReportRequest request) {
        tripReportService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        tripReportService.delete(id);
    }
}
