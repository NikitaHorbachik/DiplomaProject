package org.nharbachyk.diplomabackend.repository.jpa;

import org.nharbachyk.diplomabackend.entities.user.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByAuthority(String authority);
}
