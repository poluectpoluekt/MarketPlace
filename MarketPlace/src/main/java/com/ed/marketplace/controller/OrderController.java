package com.ed.marketplace.controller;

import com.ed.marketplace.app_class.redis.OrderIdempotencyResponse;
import com.ed.marketplace.entity.Item;
import com.ed.marketplace.service.IdempotencyService;
import com.ed.marketplace.service.OrderService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@RequestMapping("/api/orders")
@RestController
public class OrderController {

    private final OrderService orderService;
    private final IdempotencyService idempotencyService;

    /*
    метод получения суммы все товаров в корзине и формирование счета на оплату
     */
    @PostMapping(name = "/invoice")
    public BigDecimal getInvoice(HttpSession session, @RequestHeader("Idempotency-Key") @NotBlank String idempotencyKey) { // отдавать PDF

        if (idempotencyService.idempotencyKeyCheck(idempotencyKey)) {

            OrderIdempotencyResponse orderResponse = (OrderIdempotencyResponse) idempotencyService.getResultByIdempotencyKey(idempotencyKey);

            return orderResponse.getTotalAmountOrder();
        }

        return orderService.createOrder((List<Item>) session.getAttribute("basket"), idempotencyKey);
    }
}
