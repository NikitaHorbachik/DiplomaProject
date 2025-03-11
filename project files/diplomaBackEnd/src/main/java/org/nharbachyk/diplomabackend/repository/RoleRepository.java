package org.nharbachyk.diplomabackend.repository;

import org.nharbachyk.diplomabackend.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
}
