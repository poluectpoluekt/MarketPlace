package com.ed.marketplace.exception;

public class ItemNotFoundByTitleException extends RuntimeException{

    public ItemNotFoundByTitleException(String title) {
        super("Item not found by title -" + title);
    }
}
