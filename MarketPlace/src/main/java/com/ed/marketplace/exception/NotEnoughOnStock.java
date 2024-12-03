package com.ed.marketplace.exception;

public class NotEnoughOnStock extends RuntimeException{

    public NotEnoughOnStock(String nameItem){
        super("Not enough product in stock - " + nameItem);
    }
}
