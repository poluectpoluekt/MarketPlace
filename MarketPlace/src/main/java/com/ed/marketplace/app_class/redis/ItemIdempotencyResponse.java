package com.ed.marketplace.app_class.redis;

import com.ed.marketplace.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.util.ArrayList;
import java.util.List;

@RedisHash("ItemIdempotencyResponse")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemIdempotencyResponse extends BaseEntityForRedis {

    private String responseResult;
    private List<Item> itemInBasket = new ArrayList<>();


}
