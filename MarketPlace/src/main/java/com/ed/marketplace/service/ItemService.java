package com.ed.marketplace.service;

import com.ed.marketplace.app_class.redis.ItemIdempotencyResponse;
import com.ed.marketplace.dto.ItemToBasketDto;
import com.ed.marketplace.entity.Item;
import com.ed.marketplace.exception.BasketIsNullException;
import com.ed.marketplace.exception.ItemNotFoundByTitleException;
import com.ed.marketplace.exception.NotEnoughOnStockException;
import com.ed.marketplace.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final IdempotencyService idempotencyService;
    private final Logger logger = LoggerFactory.getLogger(ItemService.class);


    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    /*
    добавление товара в корзину.
    поиск товара в БД - 44 строка
    проверка наличия товара - 50 строка
    товар добавляется в лист добавление в лист
     */
    @Transactional
    public List<Item> addProductToBasket(ItemToBasketDto itemToBasketDto, List<Item> basket, String keyIdempotency) {

//        if (itemRepository.amountOnWarehouse(itemToBasketDto.getTitle()) < 1) {
//            throw new NotEnoughOnStockException(itemToBasketDto.getTitle());
//        }

        Item item = itemRepository.findById(itemToBasketDto.getId())
                .orElseThrow(() -> new ItemNotFoundByTitleException(itemToBasketDto.getTitle()));

        if (item.getItemOnWarehouse() < 1) { //проверка на наличие товара на складе
            throw new NotEnoughOnStockException(itemToBasketDto.getTitle());
        }

        if (basket == null) {
            basket = new ArrayList<>();
        }

        basket.add(item);

        idempotencyService.saveIdempotencyKey(keyIdempotency,
                new ItemIdempotencyResponse(String.format("Item %s added to basket", item.getTitle()), basket),
                3600);

//        return basket;
//
//
//        //List<Item> basket = (List<Item>) session.getAttribute("basket");
//
//        basket.add(item);
//
//        idempotencyService.saveIdempotencyKey(keyIdempotency,
//                new ItemIdempotencyResponse(String.format("Item %s added to basket", item.getTitle()), basket),
//                3600);
//
//        logger.info("Item added to Basket: {}", keyIdempotency);

        return basket;
    }

    /*
    Удаление товара в корзину.
    проверка ключа идемпотентсности
     */
    @Transactional
    public List<Item> removeProductToBasket(ItemToBasketDto itemToBasketDto, List<Item> basket, String keyIdempotency) {

//       if (session.isNew()){ return new ArrayList<>();}
//
//        List<Item> basket = (List<Item>) session.getAttribute("basket");

        if (basket == null) {
            throw new BasketIsNullException();
        }

        basket.removeIf(item -> item.getTitle().equals(itemToBasketDto.getTitle()));

        idempotencyService.saveIdempotencyKey(keyIdempotency,
                new ItemIdempotencyResponse(String.format("Item %s removed to basket", itemToBasketDto.getTitle()), basket),
                3600);

        logger.info("Item remove to Basket: {}", keyIdempotency);

        return basket;
    }
}
