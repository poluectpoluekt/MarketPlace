package com.ed.marketplace.app_class.redis;

import com.ed.marketplace.dto.CustomerResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("CustomerIdempotencyResponse")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerIdempotencyResponse extends BaseEntityForRedis {

    private CustomerResponseDto registration;

}
