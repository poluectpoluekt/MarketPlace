package com.ed.marketplace.service;

import com.ed.marketplace.dto.ItemToBasketDto;
import com.ed.marketplace.entity.CustomerDataBasketInRedis;
import com.ed.marketplace.entity.Item;
import com.ed.marketplace.exception.ItemNotFoundByTitleException;
import com.ed.marketplace.exception.NotEnoughOnStock;
import com.ed.marketplace.exception.RepeatedIdempotencyKeyException;
import com.ed.marketplace.repository.ItemRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    проверка ключа идемпотентсности
    проверка наличия товара
    поиск товара поимени и добавление в лист
     */
    @Transactional
    public List<Item> addProductToBasket(ItemToBasketDto itemToBasketDto, List<Item> basket ) {

        if(idempotencyService.idempotencyKeyCheck(itemToBasketDto.getKeyIdempotency())){
            throw new RepeatedIdempotencyKeyException(); // нужно ли отдавать ключ, который повторился?
        }

        if(itemRepository.amountOnWarehouse(itemToBasketDto.getTitle()) < 1){
            throw new NotEnoughOnStock(itemToBasketDto.getTitle());
        }

        if(basket == null){
            Item item = itemRepository.findByTitle(itemToBasketDto.getTitle())
                    .orElseThrow(() -> new ItemNotFoundByTitleException(itemToBasketDto.getTitle()));

//            List<Item> list = new ArrayList<>(); //можно ли как-то написать покороче
//            list.add(item);
            basket.add(item);
            return basket;
        }

        //List<Item> basket = (List<Item>) session.getAttribute("basket");

        basket.add(itemRepository.findByTitle(itemToBasketDto.getTitle())
                    .orElseThrow(() -> new ItemNotFoundByTitleException(itemToBasketDto.getTitle())));

        idempotencyService.saveIdempotencyKey(itemToBasketDto.getKeyIdempotency(), UUID.randomUUID().toString(), 3600);

        logger.info("Item added to Basket: {}", itemToBasketDto.getKeyIdempotency());

        return basket;
    }

    /*
    Удаление товара в корзину.
    проверка ключа идемпотентсности
    удаление
     */
    @Transactional
    public List<Item> removeProductToBasket(ItemToBasketDto itemToBasketDto, List<Item> basket) {

        if(idempotencyService.idempotencyKeyCheck(itemToBasketDto.getKeyIdempotency())){
            throw new RepeatedIdempotencyKeyException();
        }

//       if (session.isNew()){ return new ArrayList<>();}
//
//        List<Item> basket = (List<Item>) session.getAttribute("basket");

        basket.removeIf(item -> item.getTitle().equals(itemToBasketDto.getTitle()));

        logger.info("Item remove to Basket: {}", itemToBasketDto.getKeyIdempotency());

        return basket;
    }
}
