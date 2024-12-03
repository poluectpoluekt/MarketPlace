package com.ed.marketplace.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;

@RedisHash("CustomerSessionData")
@Setter
@Getter
@NoArgsConstructor
public class CustomerSessionData implements Serializable {

    @Id
    private String id;
    private long idCustomer;
    private String customerSessionId;
    private List<Item> itemsInBasket;
}
