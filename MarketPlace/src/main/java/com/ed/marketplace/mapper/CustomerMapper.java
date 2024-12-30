package com.ed.marketplace.mapper;

import com.ed.marketplace.dto.CustomerRegistrationDto;
import com.ed.marketplace.dto.CustomerResponseDto;
import com.ed.marketplace.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    Customer customerDtoToCustomer(CustomerRegistrationDto customerRegistrationDto);

    CustomerRegistrationDto customerToCustomerDto(Customer customer);

    CustomerResponseDto toCustomerResponseDto(Customer customer);
}
