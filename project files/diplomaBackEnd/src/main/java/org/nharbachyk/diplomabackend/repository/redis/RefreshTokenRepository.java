package org.nharbachyk.diplomabackend.repository.redis;

import org.nharbachyk.diplomabackend.entities.redis.RefreshTokenEntity;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshTokenEntity, String> {

    boolean existsByUsername(String username);

    boolean existsByToken(String token);

}

