package com.ed.marketplace.service;

import com.ed.marketplace.app_class.CustomerDetails;
import com.ed.marketplace.app_class.redis.CustomerIdempotencyResponse;
import com.ed.marketplace.dto.CustomerRegistrationDto;
import com.ed.marketplace.dto.CustomerResponseDto;
import com.ed.marketplace.entity.Customer;
import com.ed.marketplace.entity.enums.RoleEnums;
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
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CustomerService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    private final Argon2PasswordEncoder argon2PasswordEncoder;

    private final RoleService roleService;

    private final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    private final IdempotencyService idempotencyService;

    /*
    Создание пользователя
    проверка, что пользователь уже существует
    установка полей из DTO, установка роли User
    сохранение в БД
     */
    @Transactional
    public CustomerResponseDto createCustomer(CustomerRegistrationDto customerRegistrationDto, String keyIdempotency) {

        if (customerRepository.findByEmail(customerRegistrationDto.getEmail()).isPresent()) {
            throw new CustomerAlreadyExistsException(customerRegistrationDto.getEmail());
        }

        Customer customer = new Customer();
        customer.setFirstName(customerRegistrationDto.getFirstName());
        customer.setLastName(customerRegistrationDto.getLastName());
        customer.setEmail(customerRegistrationDto.getEmail());
        customer.setPassword(argon2PasswordEncoder.encode(customerRegistrationDto.getPassword()));
        customer.setAccountNonExpired(true);
        customer.setAccountNonLocked(true);
        customer.setCredentialsNonExpired(true);
        customer.setEnabled(true);
        customer.setRoles(Collections.singleton(roleService.getRole(RoleEnums.USER.getRole())));
        CustomerResponseDto customerRespDto = customerMapper.toCustomerResponseDto(customerRepository.save(customer));

        idempotencyService.saveIdempotencyKey(keyIdempotency, new CustomerIdempotencyResponse(customerRespDto), 3600);

        logger.info(String.format("Customer success registered - %s", customerRegistrationDto.getEmail()));

        return customerRespDto;
    }


    /*
    Метод нужный Spring Security для аутентификации пользователя
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Customer customer = customerRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));
        CustomerDetails customerDetails = new CustomerDetails();
        customerDetails.setCustomer(customer);
        Set<GrantedAuthority> auth = customer.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());

        customerDetails.setAuthorities(auth);

        return customerDetails;
    }

}
