package com.ed.marketplace.entity.kafka;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class KafkaMessage {

    private String idempotencyKeyMessage;
    private long OrderIdFromDBMarketplace;
    //private List<ItemDataForOrder> listCodeItemsForDBFromOrder = new ArrayList<>();
    private BigDecimal totalAmountOrder;
    private String ownerEmail;

}
