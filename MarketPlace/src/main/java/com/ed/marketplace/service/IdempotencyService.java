package com.ed.marketplace.service;

import com.ed.marketplace.app_class.redis.BaseEntityForRedis;
import com.ed.marketplace.exception.ErrorValueIdempotencyKeyException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class IdempotencyService {

    private final RedisTemplate<String, BaseEntityForRedis> redisTemplate;


    public boolean idempotencyKeyCheck(String idempotencyKey) {

        if (idempotencyKey.isBlank()) {
            throw new ErrorValueIdempotencyKeyException();
        }

        return redisTemplate.hasKey(idempotencyKey);

    }

    @Transactional(readOnly = true)
    public BaseEntityForRedis getResultByIdempotencyKey(String idempotencyKey) {

        return redisTemplate.opsForValue().get(idempotencyKey);
    }

    @Transactional
    public void saveIdempotencyKey(String idempotencyKey, BaseEntityForRedis resultMethod, long ttlSecond) {
        redisTemplate.opsForValue().set(idempotencyKey, resultMethod, ttlSecond, TimeUnit.SECONDS);
    }


}
