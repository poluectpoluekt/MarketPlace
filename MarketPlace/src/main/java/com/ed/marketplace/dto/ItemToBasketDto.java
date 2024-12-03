package com.ed.marketplace.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
    dto добавления товара в корзину
    название товара
    ключ, для проверки повторения запроса
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ItemToBasketDto {

    private String title;
    private String keyIdempotency;
}
