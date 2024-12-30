package com.ed.marketplace.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
    dto для регитсрации пользователя
 */
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class CustomerRegistrationDto {

    @NotEmpty(message = "FirstName should not be empty.")
    @Size(min = 6, max = 50, message = "FirstName should between 6 and 50 characters.")
    private String firstName;

    @NotEmpty(message = "LastName should not be empty.")
    @Size(min = 6, max = 50, message = "LastName should between 6 and 50 characters.")
    private String lastName;

    //@Pattern(regexp = "")
    //@NotEmpty(message = "Phone Number should not be empty.")
    @Size(min = 6, max = 50, message = "Phone Number should between 6 and 20 characters.")
    private String phoneNumber;

    @Email
    @NotEmpty(message = "Email should not be empty.")
    @Size(min = 6, max = 50, message = "Email should between 6 and 50 characters.")
    private String email;

    @NotEmpty(message = "Password should not be empty.")
    @Size(min = 6, max = 50, message = "Password should between 6 and 50 characters.")
    private String password;
}
