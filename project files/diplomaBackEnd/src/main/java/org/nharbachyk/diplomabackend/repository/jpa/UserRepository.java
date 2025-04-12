package org.nharbachyk.diplomabackend.repository.jpa;

import org.nharbachyk.diplomabackend.entities.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
    <T> List<UserEntity> findByOrganization_Id(T organization_id);
}
