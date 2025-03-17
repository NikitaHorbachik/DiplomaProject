package org.nharbachyk.diplomabackend.entities.redis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@RedisHash("AccessTokenEntity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccessTokenEntity {

    @Id
    private String username;

    @TimeToLive
    @Value("#{${security.access-token-expiration}}")
    private Long expirationInSeconds;

    private String token;

}
