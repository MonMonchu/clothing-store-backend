package com.clothingstore.dto.customer;
import jakarta.validation.constraints.*;


public record UpdateCustomerRequest (
        @Email
        String email,

        @Size(min = 8)
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$",
                message = "Password must contain at least one lowercase letter, one uppercase letter, and one digit"
        )
        String password,

        String firstName,

        String lastName,

        String address){
}