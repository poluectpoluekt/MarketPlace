package com.ed.marketplace.exception;

public class ErrorCreatedInvoiceBasketIsNullException extends RuntimeException {

    public ErrorCreatedInvoiceBasketIsNullException() {
        super("Error created invoice basket is null");
    }
}
