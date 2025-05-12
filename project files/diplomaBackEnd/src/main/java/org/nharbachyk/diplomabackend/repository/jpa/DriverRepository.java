package org.nharbachyk.diplomabackend.repository.jpa;

import org.nharbachyk.diplomabackend.entities.tripReport.DriverEntity;
import org.nharbachyk.diplomabackend.entities.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DriverRepository extends JpaRepository<DriverEntity, Long> {

    boolean existsByUser(UserEntity user);

    void deleteByUser(UserEntity user);

    Optional<DriverEntity> findByUser_Id(Long userId);

    <T> List<DriverEntity> findAllByUser_Organization_Id(T user_organization_id);
}
