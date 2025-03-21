package org.nharbachyk.diplomabackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final RedisTemplate<String, String> redisTemplate;

    public void saveRefreshToken(String username, String refreshToken) {
        redisTemplate.opsForValue().set(username, refreshToken);
    }

    public String getRefreshToken(String username) {
        return redisTemplate.opsForValue().get(username);
    }

    public void deleteRefreshToken(String username) {
        redisTemplate.delete(username);
    }
}
