package com.clothingstore.dto.customer;

import jakarta.validation.constraints.*;

public record CreateCustomerRequest (
        @NotBlank
        @Email
        String email,

        @NotBlank
        @Size(min = 8)
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$",
                message = "Password must contain at least one lowercase letter, one uppercase letter, and one digit"
        )
        String password,

        @NotBlank
        String firstName,

        @NotBlank
        String lastName,

        String address){
}
