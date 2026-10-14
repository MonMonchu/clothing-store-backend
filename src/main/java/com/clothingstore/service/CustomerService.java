package com.clothingstore.service;

import com.clothingstore.dto.customer.*;
import com.clothingstore.entity.Customer;
import com.clothingstore.mapper.CustomerMapper;
import com.clothingstore.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.UUID;


@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomerService(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public CustomerResponse addCustomer(CreateCustomerRequest dto) {
        String email = dto.email().trim();

        if (customerRepository.existsByEmailIgnoreCase(email)) {
            throw new IllegalArgumentException("Email already in use");
        }

        String hashed = passwordEncoder.encode(dto.password());

        Customer customer = CustomerMapper.toEntity(dto, hashed);

        Customer saved = customerRepository.save(customer);

        return CustomerMapper.toDto(saved);
    }

    public CustomerResponse getCustomerById(UUID id) {
        Customer customer = getCustomerOrThrow(id);
        return CustomerMapper.toDto(customer);

    }

    @Transactional
    public CustomerResponse updateCustomer(
            UUID id,
            UpdateCustomerRequest dto) {

        Customer customer = getCustomerOrThrow(id);

        if (dto.email() != null && !dto.email().isBlank()) {
            String newEmail = dto.email().trim();

            if (customerRepository.existsByEmailIgnoreCase(newEmail)
                    && !newEmail.equalsIgnoreCase(customer.getEmail())) {
                throw new IllegalArgumentException("Email already in use");
            }
        }

        if (dto.password() != null && !dto.password().isBlank()) {
            String hashed = passwordEncoder.encode(dto.password());
            customer.changePassword(hashed);
        }

        CustomerMapper.updateEntity(customer, dto);

        return CustomerMapper.toDto(customer);
    }

    public void deleteCustomer(UUID id) {
        Customer customer = getCustomerOrThrow(id);

        customerRepository.delete(customer);

    }

    public List<CustomerResponse> getAllCustomers(){
        return customerRepository.findAll().stream().map(CustomerMapper::toDto).toList();
    }

    public Customer getCustomerOrThrow(UUID id){
        return customerRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Customer not found"));
    }

}
