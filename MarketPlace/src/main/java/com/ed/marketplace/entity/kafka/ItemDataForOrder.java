package com.ed.marketplace.entity.kafka;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

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
