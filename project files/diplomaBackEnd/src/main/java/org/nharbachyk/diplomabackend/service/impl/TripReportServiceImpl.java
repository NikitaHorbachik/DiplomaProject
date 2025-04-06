package org.nharbachyk.diplomabackend.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.nharbachyk.diplomabackend.controller.request.tripReport.CreateTripReportRequest;
import org.nharbachyk.diplomabackend.controller.request.tripReport.UpdateTripReportRequest;
import org.nharbachyk.diplomabackend.controller.response.tripReport.TripReportResponse;
import org.nharbachyk.diplomabackend.entities.tripReport.DriverEntity;
import org.nharbachyk.diplomabackend.entities.tripReport.TripReportEntity;
import org.nharbachyk.diplomabackend.mapper.TripReportMapper;
import org.nharbachyk.diplomabackend.repository.jpa.DriverRepository;
import org.nharbachyk.diplomabackend.repository.jpa.TripReportRepository;
import org.nharbachyk.diplomabackend.service.TripReportService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TripReportServiceImpl implements TripReportService {

    private final TripReportRepository tripReportRepository;
    private final DriverRepository driverRepository;
    private final TripReportMapper tripReportMapper;

    @Override
    public Long create(CreateTripReportRequest request) {
        DriverEntity driver = getDriverOrThrow(request.driverId());

        TripReportEntity tripReportEntity = TripReportEntity.builder()
                .driver(driver)
                .cargoId(request.cargoId())
                .startLocation(request.startLocation())
                .endLocation(request.endLocation())
                .startDatetime(request.startDatetime())
                .endDatetime(request.endDatetime())
                .totalFuelConsumed(request.totalFuelConsumed())
                .build();

        TripReportEntity savedReport = tripReportRepository.save(tripReportEntity);
        return savedReport.getId();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<TripReportResponse> findAll(Pageable pageable) {
        return tripReportRepository.findAll(pageable).map(tripReportMapper::toResponse);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<TripReportResponse> findByDriver(Long driverId, Pageable pageable) {
        return tripReportRepository.findByDriver_Id(driverId, pageable).map(tripReportMapper::toResponse);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<TripReportResponse> findAllByCargoId(String cargoId, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return tripReportRepository.findAllByCargoIdAndStartDatetimeAfterAndEndDatetimeBefore(cargoId,
                startDate,
                endDate,
                pageable)
                .map(tripReportMapper::toResponse);
    }

    @Transactional(readOnly = true)
    @Override
    public TripReportResponse findById(Long id) {
        return tripReportRepository.findById(id)
                .map(tripReportMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Trip report not found with id: " + id));
    }

    @Transactional(readOnly = true)
    @Override
    public List<TripReportResponse> findAllByDriverAndDate(Long driverId, LocalDateTime from, LocalDateTime to) {
        return tripReportRepository
                .findAllByDriver_IdAndStartDatetimeAfterAndEndDatetimeBefore(driverId, from, to)
                .stream()
                .map(tripReportMapper::toResponse).toList();
    }

    @Override
    public void update(Long id, UpdateTripReportRequest request) {
        TripReportEntity tripReport = tripReportRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Trip report not found with id: " + id));
        tripReportMapper.updateFromRequest(request, tripReport);
        tripReportRepository.save(tripReport);
    }

    @Override
    public void delete(Long id) {
        tripReportRepository.deleteById(id);
    }

    private DriverEntity getDriverOrThrow(Long driverId) {
        return driverRepository.findById(driverId)
                .orElseThrow(() -> new EntityNotFoundException("Driver not found with id: " + driverId));
    }
}