package com.ed.orderservice.entity.kafka;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MessageForProcessingOrder {

    private String idempotencyKeyMessage;
    private long OrderIdFromDBMarketplace;
    //private List<ItemDataForOrder> listCodeItemsForDBFromOrder = new ArrayList<>();
    private BigDecimal totalAmountOrder;
    private String ownerEmail;
}
