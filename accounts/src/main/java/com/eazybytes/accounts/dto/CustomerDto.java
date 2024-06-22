package com.eazybytes.accounts.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
public class CustomerDto {
    @NotEmpty(message = "Name can not be a null or empty")
    private String name;
    @NotEmpty(message = "Email address can not be a null or empty")
    private String email;
    @NotEmpty(message = "Mobile number must be required")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String mobileNumber;
    private AccountsDto accountsDto;
}
