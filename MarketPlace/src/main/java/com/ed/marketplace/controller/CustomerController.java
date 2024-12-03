package com.ed.marketplace.controller;

import com.ed.marketplace.dto.CustomerRegistrationDto;
import com.ed.marketplace.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    //    @GetMapping()
//    public void get(){
//        Cookie cookie = new Cookie("data", "cookie_data");
//        cookie.setMaxAge(86400);
//
//    }
    /*
        метод регистрации пользователя
     */
    @PostMapping("/registration")
    public CustomerRegistrationDto createCustomer(@RequestBody CustomerRegistrationDto customerRegistrationDto) {
        return customerService.createCustomer(customerRegistrationDto);
    }


}
