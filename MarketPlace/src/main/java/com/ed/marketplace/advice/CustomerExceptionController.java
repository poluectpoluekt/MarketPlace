package com.ed.marketplace.advice;

import com.ed.marketplace.exception.CustomerAlreadyExistsException;
import com.ed.marketplace.exception.response.CustomerResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomerExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CustomerAlreadyExistsException.class)
    public CustomerResponse customerAlreadyExists(CustomerAlreadyExistsException e) {
        String messageException = String.format("This \"%s\" already exists.", e.getMessage());
        return new CustomerResponse(messageException);
    }
}
