package com.ed.marketplace.controller;

import com.ed.marketplace.dto.ItemToBasketDto;
import com.ed.marketplace.entity.Item;
import com.ed.marketplace.service.ItemService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RequestMapping("/api/items")
@RestController
public class ItemController {

    private ItemService productService;

    /*
        метод добавления одного товара в корзину пользователя
     */
    @PostMapping(name = "/add")
    public void addItem(@RequestBody ItemToBasketDto itemToBasketDto, HttpSession session) {

        //List<Item> basket = productService.addProductToBasket(itemToBasketDto, session);
        List<Item> basket = productService.addProductToBasket(itemToBasketDto, (List<Item>) session.getAttribute("basket"));
        session.setAttribute("basket", basket);
        session.setMaxInactiveInterval(365000);
    }

    /*
        метод удаления одного??? товара в корзину пользователя
     */
    @PostMapping(name = "/remove")
    public void removeItem(@RequestBody ItemToBasketDto itemToBasketDto, HttpSession session) {

        if(session.isNew()){
            session.setAttribute("basket", new ArrayList<>());
        }

        session.setAttribute("basket", productService.removeProductToBasket(itemToBasketDto, (List<Item>) session.getAttribute("basket") ));
        session.setMaxInactiveInterval(365000);
    }
}
