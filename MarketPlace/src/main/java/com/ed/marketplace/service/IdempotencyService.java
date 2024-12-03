package com.ed.marketplace.service;

import com.ed.marketplace.exception.ErrorValueIdempotencyKeyException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class IdempotencyService {

    private final StringRedisTemplate redisTemplate;


    public boolean idempotencyKeyCheck(String idempotencyKey) {

        if(idempotencyKey == null || idempotencyKey.isEmpty()) {
            throw new ErrorValueIdempotencyKeyException();
        }

        return redisTemplate.hasKey(idempotencyKey);

    }

    @Transactional
    public void saveIdempotencyKey(String idempotencyKey, String logResultOperation, long ttlSecond) {
        redisTemplate.opsForValue().set(idempotencyKey, logResultOperation, ttlSecond, TimeUnit.SECONDS);
    }


}
