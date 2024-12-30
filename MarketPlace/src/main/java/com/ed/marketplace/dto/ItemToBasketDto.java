package com.ed.marketplace.dto;

import jakarta.validation.constraints.NotEmpty;
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

    @NotEmpty(message = "Id should not be empty.")
    private long id;

    @NotEmpty(message = "Title should not be empty.")
    private String title;
}
