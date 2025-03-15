package org.nharbachyk.diplomabackend.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@RedisHash
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccessTokenEntity extends BaseEntity<Long> {

    @TimeToLive
    @Value("${security.access-token-expiration}")
    private long expirationTime;

    private String username;
    private String token;
}
