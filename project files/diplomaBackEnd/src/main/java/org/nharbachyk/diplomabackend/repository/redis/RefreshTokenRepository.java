package org.nharbachyk.diplomabackend.repository.redis;

import org.nharbachyk.diplomabackend.entities.RefreshTokenEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends CrudRepository<RefreshTokenEntity, Long> {

    Optional<RefreshTokenEntity> findByUsername(String username);

    void deleteByUsername(String username);

}
