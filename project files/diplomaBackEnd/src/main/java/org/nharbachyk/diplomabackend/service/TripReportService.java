package org.nharbachyk.diplomabackend.service;

import jakarta.validation.Valid;
import org.nharbachyk.diplomabackend.controller.request.tripReport.CreateTripReportRequest;
import org.nharbachyk.diplomabackend.controller.request.tripReport.UpdateTripReportRequest;
import org.nharbachyk.diplomabackend.controller.response.tripReport.TripReportResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface TripReportService {

    Long create(CreateTripReportRequest request);

    Page<TripReportResponse> findAll(Pageable pageable);

    Page<TripReportResponse> findByDriver(Long driverId, Pageable pageable);

    Page<TripReportResponse> findAllByCargoId(String cargoId, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    TripReportResponse findById(Long id);

    List<TripReportResponse> findAllByDriverAndDate(Long driverId, LocalDateTime from, LocalDateTime to);

    void update(Long id, @Valid UpdateTripReportRequest request);

    void delete(Long id);
}