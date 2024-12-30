package com.ed.marketplace.service;

import com.ed.marketplace.app_class.redis.OrderIdempotencyResponse;
import com.ed.marketplace.entity.Customer;
import com.ed.marketplace.entity.Item;
import com.ed.marketplace.entity.Order;
import com.ed.marketplace.entity.enums.OrderStatus;
import com.ed.marketplace.entity.kafka.KafkaMessCreateOrder;
import com.ed.marketplace.exception.ErrorCreatedInvoiceBasketIsNullException;
import com.ed.marketplace.exception.RepeatedIdempotencyKeyException;
import com.ed.marketplace.mapper.ItemDataMapper;
import com.ed.marketplace.repository.CustomerRepository;
import com.ed.marketplace.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final KafkaTemplate<String, KafkaMessCreateOrder> kafkaTemplate;
    private final NewTopic topicOrders;
    private final ItemDataMapper itemDataMapper;
    private final IdempotencyService idempotencyService;
    private final Logger logger = LoggerFactory.getLogger(OrderService.class);


    /*
    Создание заказа
    получаем Authentication, чтобы достать его почту
    проверяем пустоту корзины
    проверяем ключ идемпотентности
    Получаем корзину из сессии пользователя
    Создает сущность заказа, считает общую сумму
     */
    @Transactional
    public BigDecimal createOrder(List<Item> basketItem, String keyIdempotency) {

        Authentication authCustomer = SecurityContextHolder.getContext().getAuthentication();

        if (basketItem == null) {
            throw new ErrorCreatedInvoiceBasketIsNullException();
        }

        if (idempotencyService.idempotencyKeyCheck(keyIdempotency)) {
            throw new RepeatedIdempotencyKeyException();
        }

        Order order = new Order();

        Customer customer = customerRepository.findByEmail(authCustomer.getName())
                .orElseThrow(() -> new UsernameNotFoundException(authCustomer.getName()));

        //List<Item> items = (List<Item>) session.getAttribute("basket");

        BigDecimal totalPriceItemsInOrder = BigDecimal.ZERO;
        basketItem.forEach(i -> totalPriceItemsInOrder.add(i.getPriceItem()));

        order.setCustomerOwner(customer);
        order.setItemsOnOrder(basketItem);
        order.setOrderStatus(OrderStatus.PENDING);
        order.setTotalAmountOrder(totalPriceItemsInOrder);

        //List<ItemDataForOrder> listForOrderService = items.stream().map(itemDataMapper::itemToItemDataForOrder).toList();

        KafkaMessCreateOrder newMessageForProcessingOrder = new KafkaMessCreateOrder();
        newMessageForProcessingOrder.setOrderIdFromDBMarketplace(orderRepository.save(order).getId());
        newMessageForProcessingOrder.setTotalAmountOrder(totalPriceItemsInOrder);
        newMessageForProcessingOrder.setOwnerEmail(order.getCustomerOwner().getEmail());

        //String idempotencyKey = getIdempotencyKey(String.valueOf(customer.getId()), "");
        //newMessageForProcessingOrder.setIdempotencyKeyMessage(idempotencyKey);
        //newMessageForProcessingOrder.setListCodeItemsForDBFromOrder(listForOrderService);
        //Pageable pageable = PageRequest.of(0, basketItem.size());
        kafkaTemplate.send(topicOrders.name(), newMessageForProcessingOrder);

        idempotencyService.saveIdempotencyKey(keyIdempotency, new OrderIdempotencyResponse(totalPriceItemsInOrder), 3600);

        return order.getTotalAmountOrder();
    }


    /*
    Слушатель сообщений из kafka, что заказ был принят в работу и отправлен
     */
    @KafkaListener(topics = "AlertClosedInvoiceFromOrderService", groupId = "main_group_order")
    @Transactional
    public void changeStatusOfOrder(@Payload long idInvoice) {
        
        Order order = orderRepository.findById(idInvoice).orElse(null);
        if (order == null) {
            logger.info("Error found order");
            return;
        }
        order.setOrderStatus(OrderStatus.SENT);
        orderRepository.save(order);
    }

//    public void returnResult(String idempotencyKey) {
//        return redisTemplate.opsForValue().get(idempotencyKey);
//    }


}
