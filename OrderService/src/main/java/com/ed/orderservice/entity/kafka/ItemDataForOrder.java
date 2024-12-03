package com.ed.orderservice.entity.kafka;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ItemDataForOrder {

    private String itemTitle;
    private BigDecimal priceItem;
    private int itemQuantity;
}
