package com.ed.orderservice.entity.kafka;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
