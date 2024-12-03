package com.ed.marketplace.exception;

public class CustomerAlreadyExistsException extends RuntimeException{

    public CustomerAlreadyExistsException(String message) {
        super(message);
    }
}
