package org.nharbachyk.diplomabackend.mapper;

import lombok.RequiredArgsConstructor;
import org.nharbachyk.diplomabackend.controller.request.tripReport.CreateTripReportRequest;
import org.nharbachyk.diplomabackend.controller.request.tripReport.UpdateTripReportRequest;
import org.nharbachyk.diplomabackend.controller.response.tripReport.TripReportResponse;
import org.nharbachyk.diplomabackend.entities.tripReport.DriverEntity;
import org.nharbachyk.diplomabackend.entities.tripReport.TripReportEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TripReportMapper {

    private final DriverMapper driverMapper;

    public TripReportEntity toEntity(CreateTripReportRequest request) {
        DriverEntity driver = new DriverEntity();
        driver.setId(request.driverId());

        return TripReportEntity.builder()
                .driver(driver)
                .cargoId(request.cargoId())
                .startLocation(request.startLocation())
                .endLocation(request.endLocation())
                .startDatetime(request.startDatetime())
                .endDatetime(request.endDatetime())
                .totalFuelConsumed(request.totalFuelConsumed())
                .distanceKm(request.distanceKm())
                .build();
    }

    public TripReportResponse toResponse(TripReportEntity entity) {
        return new TripReportResponse(
                entity.getId(),
                entity.getDriver().getId(),
                entity.getDriver().getFullName(),
                entity.getCargoId(),
                entity.getStartLocation(),
                entity.getEndLocation(),
                entity.getDistanceKm(),
                entity.getStartDatetime(),
                entity.getEndDatetime(),
                entity.calculateAverageSpeed(),
                entity.getTotalFuelConsumed()
        );
    }

    public void updateFromRequest(UpdateTripReportRequest request, TripReportEntity entity) {
        if (request.cargoId() != null) {
            entity.setCargoId(request.cargoId());
        }
        if (request.endLocation() != null) {
            entity.setEndLocation(request.endLocation());
        }
        if (request.endDatetime() != null) {
            entity.setEndDatetime(request.endDatetime());
        }
        if (request.totalFuelConsumed() != null) {
            entity.setTotalFuelConsumed(request.totalFuelConsumed());
        }
    }
}