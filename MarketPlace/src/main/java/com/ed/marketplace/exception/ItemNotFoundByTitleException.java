package com.ed.marketplace.exception;

public class ItemNotFoundByTitleException extends RuntimeException {

    public ItemNotFoundByTitleException(String title) {
        super(String.format("Item not found by title - %s", title));
    }
}
