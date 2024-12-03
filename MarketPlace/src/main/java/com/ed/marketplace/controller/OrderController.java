package com.ed.marketplace.controller;

import com.ed.marketplace.entity.Item;
import com.ed.marketplace.service.OrderService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@RequestMapping("/api/orders")
@RestController
public class OrderController {

    private final OrderService orderService;

    /*
    метод получения суммы все товаров в корзине и формирование счета на оплату
     */
    @PostMapping(name = "/invoice")
    public BigDecimal getInvoice(HttpSession session, @RequestParam String idempotencyKey) { // отдавать PDF

        return orderService.createOrder((List<Item>) session.getAttribute("basket"), idempotencyKey);
    }
}
