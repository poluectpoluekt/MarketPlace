package com.ed.marketplace.exception;

public class BasketIsNullException extends RuntimeException {

    public BasketIsNullException() {
        super(String.format("Basket is null!"));
    }
}
