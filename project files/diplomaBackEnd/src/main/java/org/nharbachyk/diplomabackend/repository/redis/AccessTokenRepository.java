package org.nharbachyk.diplomabackend.repository.redis;

import org.nharbachyk.diplomabackend.entities.AccessTokenEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccessTokenRepository extends CrudRepository<AccessTokenEntity, Long> {

    Optional<AccessTokenEntity> findByUsername(String username);

    void deleteByUsername(String username);

}
