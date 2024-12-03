package com.ed.marketplace.service;

import com.ed.marketplace.dto.CustomerRegistrationDto;
import com.ed.marketplace.entity.Customer;
import com.ed.marketplace.exception.CustomerAlreadyExistsException;
import com.ed.marketplace.mapper.CustomerMapper;
import com.ed.marketplace.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CustomerService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final RoleService roleService;

    Logger logger = LoggerFactory.getLogger(CustomerService.class);

    /*
    Создание пользователя
    проверка, что пользователь уже существует
    установка полей из DTO, установка роли User
    сохранение в БД
     */

    @Transactional
    public CustomerRegistrationDto createCustomer(CustomerRegistrationDto customerRegistrationDto) {
        if(customerRepository.findByEmail(customerRegistrationDto.getEmail()).isPresent()) {
            throw new CustomerAlreadyExistsException(customerRegistrationDto.getEmail());
        }

        Customer customer = new Customer();
        customer.setFirstName(customerRegistrationDto.getFirstName());
        customer.setLastName(customerRegistrationDto.getLastName());
        customer.setEmail(customerRegistrationDto.getEmail());
        customer.setPassword(bCryptPasswordEncoder.encode(customerRegistrationDto.getPassword()));
        customer.setAccountNonExpired(true);
        customer.setAccountNonLocked(true);
        customer.setCredentialsNonExpired(true);
        customer.setEnabled(true);
        customer.setRoles(Collections.singleton(roleService.getRole("ROLE_CUSTOMER")));

        return customerMapper.customerToCustomerDto(customerRepository.save(customer));
    }



    /*
    Метод нужный Spring Security для аутентификации пользователя
     */

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Customer customer = customerRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));
        Set<GrantedAuthority> auth = customer.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());

        customer.setAuthorities(auth);

        return customer;
    }


}
