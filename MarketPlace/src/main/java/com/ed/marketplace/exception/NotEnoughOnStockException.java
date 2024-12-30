package com.ed.marketplace.exception;

public class NotEnoughOnStockException extends RuntimeException {

    public NotEnoughOnStockException(String nameItem) {
        super(String.format("Not enough product in stock - %s", nameItem));
    }
}
