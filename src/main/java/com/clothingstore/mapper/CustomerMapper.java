package com.clothingstore.mapper;

import com.clothingstore.dto.customer.*;
import com.clothingstore.entity.Customer;


public class CustomerMapper {

    public static Customer toEntity(CreateCustomerRequest dto, String hashedPassword){
        return new Customer(
                dto.email(),
                hashedPassword,
                dto.firstName(),
                dto.lastName(),
                dto.address()
        );
    }
    public static void updateEntity(
            Customer customer,
            UpdateCustomerRequest dto) {

        if (dto.email() != null && !dto.email().isBlank()) {
            customer.setEmail(dto.email());
        }

        if (dto.firstName() != null && !dto.firstName().isBlank()) {
            customer.setFirstName(dto.firstName());
        }

        if (dto.lastName() != null && !dto.lastName().isBlank()) {
            customer.setLastName(dto.lastName());
        }

        if (dto.address() != null && !dto.address().isBlank()) {
            customer.setAddress(dto.address());
        }
    }
    public static CustomerResponse toDto(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getEmail(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getAddress()
        );
    }


}
