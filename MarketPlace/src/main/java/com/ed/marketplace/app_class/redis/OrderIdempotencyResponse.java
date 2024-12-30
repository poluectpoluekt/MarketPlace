package com.ed.marketplace.app_class.redis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.math.BigDecimal;

@RedisHash("OrderIdempotencyResponse")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderIdempotencyResponse extends BaseEntityForRedis {

    BigDecimal totalAmountOrder;

}
