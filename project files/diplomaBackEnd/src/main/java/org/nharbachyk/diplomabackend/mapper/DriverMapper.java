package org.nharbachyk.diplomabackend.mapper;

import lombok.RequiredArgsConstructor;
import org.nharbachyk.diplomabackend.controller.request.user.CreateDriverRequest;
import org.nharbachyk.diplomabackend.controller.response.user.DriverResponse;
import org.nharbachyk.diplomabackend.entities.tripReport.DriverEntity;
import org.nharbachyk.diplomabackend.entities.user.UserEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DriverMapper {

    private final UserMapper userMapper;

    public DriverEntity toEntity(CreateDriverRequest createDriverRequest) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(createDriverRequest.userId());
        return DriverEntity.builder()
                .user(userEntity)
                .phone(createDriverRequest.phone())
                .licenseNumber(createDriverRequest.licenseNumber())
                .build();
    }

    public DriverResponse toResponse(DriverEntity driverEntity) {
        return new DriverResponse(
                driverEntity.getUser().getId(),
                driverEntity.getPhone(),
                driverEntity.getLicenseNumber()
        );
    }

}
