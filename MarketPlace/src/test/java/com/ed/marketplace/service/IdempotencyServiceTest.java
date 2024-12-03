package com.ed.marketplace.service;


import com.ed.marketplace.exception.ErrorValueIdempotencyKeyException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.StringRedisTemplate;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class IdempotencyServiceTest {

    @Mock
    StringRedisTemplate redisTemplate;

    @InjectMocks
    IdempotencyService idempotencyService;

    @DisplayName("Возврат true, если key не пустой или не null, и найден в БД Redis")
    @Test
    void testIdempotencyKeyCheck_whenKeyIsNotNull_returnTrue() {

        String key = "o3r5-c3r32-m5d4";

        Mockito.when(redisTemplate.hasKey(key)).thenReturn(true);

        assertTrue(idempotencyService.idempotencyKeyCheck(key));
    }


    @DisplayName("Возврат исключения, если key равен null")
    @Test
    void testIdempotencyKeyCheck_whenKeyIsNull_ThrowsException() {

        String key = null;

        assertThrows(ErrorValueIdempotencyKeyException.class, () -> idempotencyService.idempotencyKeyCheck(key));
    }


//    @Test
//    void saveIdempotencyKey() {
//    }

}