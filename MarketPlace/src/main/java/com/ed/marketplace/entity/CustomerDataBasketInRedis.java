package com.ed.marketplace.entity;

import org.springframework.data.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@RedisHash("CustomerDataBasketInRedis")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDataBasketInRedis implements Serializable {

    @Id
    private String customerSessionId;
    private List<Item> itemsInBasket = new ArrayList<>();
}
