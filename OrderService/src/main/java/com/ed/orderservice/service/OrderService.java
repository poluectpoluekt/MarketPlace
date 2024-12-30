package com.ed.orderservice.service;

import com.ed.orderservice.entity.Invoice;
import com.ed.orderservice.entity.enums.StatusInvoice;
import com.ed.orderservice.entity.kafka.MessageForProcessingOrder;
import com.ed.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final NewTopic topic;

    @KafkaListener(topics = "OrdersFromMarketPlace", groupId = "main_group_order")
    @Transactional
    public void acceptTheOrderForProcessing(@Payload MessageForProcessingOrder messageForProcessingOrder) {

        // проверка ключа, нужна ли?
        Invoice order = new Invoice();
        order.setIdInMarketplaceService(messageForProcessingOrder.getOrderIdFromDBMarketplace());
        order.setOwnerEmail(messageForProcessingOrder.getOwnerEmail());
        order.setTotalAmount(messageForProcessingOrder.getTotalAmountOrder());
        order.setInvoiceDate(Instant.now());
        order.setInvoiceStatus(StatusInvoice.PROCESSING);

        orderRepository.save(order);
    }


    @Transactional
    @Scheduled(fixedRate = 10000)
    public void changeStatusInOrder() {

        Invoice openInvoice = orderRepository.findByInvoiceStatusAndAsc(StatusInvoice.PROCESSING.toString()).orElseGet(null);
        if (openInvoice == null) {
            logger.info("Invoice not found");
            return;
        }

        openInvoice.setInvoiceStatus(StatusInvoice.PROCESSING);
        orderRepository.save(openInvoice);
        kafkaTemplate.send(topic.name(), openInvoice.getIdInMarketplaceService());
        // генерация ключа
        // kafka send

    }
}
