package com.ed.marketplace.service;

import com.ed.marketplace.dto.CustomerRegistrationDto;
import com.ed.marketplace.dto.CustomerResponseDto;
import com.ed.marketplace.entity.Customer;
import com.ed.marketplace.repository.CustomerRepository;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Inject
    CustomerService customerService;

    @Mock
    CustomerRepository customerRepository;

    @Test
    void createCustomer_whenCustomerNoExists_returnCustomerResponseDto() {

        CustomerRegistrationDto customerRegDtoInput =
                new CustomerRegistrationDto("first name", "middle name", null,
                        "customer@gmail.com", "password");

        Customer customer = new Customer();

        CustomerResponseDto customerRegDtoResult = customerService.createCustomer(new CustomerRegistrationDto(), "keyIdempotency");

        Mockito.when(customerRepository.findByEmail(customerRegDtoInput.getEmail())).thenReturn(Optional.empty());

        CustomerResponseDto expectedCustomerRegDto = new CustomerResponseDto();

        Assertions.assertEquals(expectedCustomerRegDto, customerRegDtoResult);

    }
}