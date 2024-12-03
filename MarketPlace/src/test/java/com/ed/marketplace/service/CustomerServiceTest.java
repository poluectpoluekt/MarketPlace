package com.ed.marketplace.service;

import com.ed.marketplace.dto.CustomerRegistrationDto;
import com.ed.marketplace.entity.Customer;
import com.ed.marketplace.repository.CustomerRepository;
import jakarta.inject.Inject;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Inject
    CustomerService customerService;

    @Mock
    CustomerRepository customerRepository;

    @Test
    void createCustomer_whenCustomerNoExists_returnCustomerRegDto() {

        CustomerRegistrationDto customerRegDtoInput =
                new CustomerRegistrationDto("first name", "middle name",null,
                        "customer@gmail.com", "password");

        Customer customer = new Customer();

        CustomerRegistrationDto customerRegDtoResult = customerService.createCustomer(new CustomerRegistrationDto());

        Mockito.when(customerRepository.findByEmail(customerRegDtoInput.getEmail())).thenReturn(Optional.empty());

        CustomerRegistrationDto  expectedCustomerRegDto = new CustomerRegistrationDto();

        Assertions.assertEquals(expectedCustomerRegDto, customerRegDtoResult);

    }
}