package com.ed.marketplace.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CustomerSignInDto {

    @Email
    @NotEmpty(message = "Email should not be empty.")
    @Size(min = 6, max = 50, message = "Email should between 6 and 50 characters.")
    private String email;
    
    @NotEmpty(message = "Password should not be empty.")
    @Size(min = 6, max = 50, message = "Password should between 6 and 50 characters.")
    private String password;
}
