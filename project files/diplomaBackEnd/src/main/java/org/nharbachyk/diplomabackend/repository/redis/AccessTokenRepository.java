package org.nharbachyk.diplomabackend.repository.redis;

import org.nharbachyk.diplomabackend.entities.redis.AccessTokenEntity;
import org.springframework.data.repository.CrudRepository;

public interface AccessTokenRepository extends CrudRepository<AccessTokenEntity, String> {

    boolean existsByUsername(String username);

}
