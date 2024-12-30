package com.ed.marketplace.controller;

import com.ed.marketplace.app_class.redis.CustomerIdempotencyResponse;
import com.ed.marketplace.dto.CustomerRegistrationDto;
import com.ed.marketplace.dto.CustomerResponseDto;
import com.ed.marketplace.service.CustomerService;
import com.ed.marketplace.service.IdempotencyService;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final IdempotencyService idempotencyService;

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
    public CustomerResponseDto createCustomer(@RequestBody CustomerRegistrationDto customerRegistrationDto,
                                              @RequestHeader("Idempotency-Key") @NotBlank String idempotencyKey) {


        if (idempotencyService.idempotencyKeyCheck(idempotencyKey)) {

            CustomerIdempotencyResponse responseCustomerRegistration =
                    (CustomerIdempotencyResponse) idempotencyService.getResultByIdempotencyKey(idempotencyKey);

            return responseCustomerRegistration.getRegistration();
        }

        return customerService.createCustomer(customerRegistrationDto, idempotencyKey);
    }


}
