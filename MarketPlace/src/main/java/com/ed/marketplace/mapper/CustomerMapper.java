package com.ed.marketplace.mapper;

import com.ed.marketplace.dto.CustomerRegistrationDto;
import com.ed.marketplace.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    Customer customerDtoToCustomer(CustomerRegistrationDto customerRegistrationDto);

    @Mapping(target = "password", ignore = true)
    CustomerRegistrationDto customerToCustomerDto(Customer customer);
}
