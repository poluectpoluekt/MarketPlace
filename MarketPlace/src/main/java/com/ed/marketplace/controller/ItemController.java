package com.ed.marketplace.controller;

import com.ed.marketplace.app_class.redis.ItemIdempotencyResponse;
import com.ed.marketplace.dto.ItemToBasketDto;
import com.ed.marketplace.entity.Item;
import com.ed.marketplace.service.IdempotencyService;
import com.ed.marketplace.service.ItemService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RequestMapping("/api/items")
@RestController
public class ItemController {

    private ItemService productService;
    private final static int MAX_INTERVAL = 365000;
    private final IdempotencyService idempotencyService;

    /*
        метод добавления одного товара в корзину пользователя
     */
    @PostMapping(name = "/add")
    public void addItem(@RequestBody ItemToBasketDto itemToBasketDto, HttpSession session,
                        @RequestHeader("Idempotency-Key") @NotBlank String idempotencyKey) {

        if (idempotencyService.idempotencyKeyCheck(idempotencyKey)) {

            ItemIdempotencyResponse responseItemAddToBasket =
                    (ItemIdempotencyResponse) idempotencyService.getResultByIdempotencyKey(idempotencyKey);

            session.setAttribute("basket", responseItemAddToBasket.getItemInBasket());
            return;
        }

        //List<Item> basket = productService.addProductToBasket(itemToBasketDto, session);
        List<Item> basket = productService.addProductToBasket(itemToBasketDto, (List<Item>) session.getAttribute("basket"), idempotencyKey);
        session.setAttribute("basket", basket);
        session.setMaxInactiveInterval(MAX_INTERVAL);
    }

    /*
        метод удаления одного??? товара в корзину пользователя
     */
    @PostMapping(name = "/remove")
    public void removeItem(@RequestBody ItemToBasketDto itemToBasketDto, HttpSession session,
                           @RequestHeader("Idempotency-Key") @NotBlank String idempotencyKey) {

        if (idempotencyService.idempotencyKeyCheck(idempotencyKey)) { // проверка ключа idempotency

            ItemIdempotencyResponse responseItemRemoveToBasket =
                    (ItemIdempotencyResponse) idempotencyService.getResultByIdempotencyKey(idempotencyKey);
            session.setAttribute("basket", responseItemRemoveToBasket);
            return;
        }

        if (session.isNew()) {
            session.setAttribute("basket", new ArrayList<>());
        }

        session.setAttribute("basket", productService.removeProductToBasket(itemToBasketDto, (List<Item>) session.getAttribute("basket"), idempotencyKey));
        session.setMaxInactiveInterval(365000);
    }
}
