package com.clothingstore.controller;

import com.clothingstore.dto.customer.*;
import com.clothingstore.dto.order.OrderResponse;
import com.clothingstore.service.CustomerService;
import com.clothingstore.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;
    private final OrderService orderService;

    public CustomerController(CustomerService customerService, OrderService orderService){
        this.customerService = customerService;
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public CustomerResponse getById(@PathVariable UUID id){
        return customerService.getCustomerById(id);
    }
    @PostMapping
    public CustomerResponse addCustomer(@Valid @RequestBody CreateCustomerRequest dto){
        return customerService.addCustomer(dto);
    }

    @PatchMapping("/{id}")
    public CustomerResponse update( @PathVariable UUID id, @Valid @RequestBody UpdateCustomerRequest dto){
        return customerService.updateCustomer(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id){
        customerService.deleteCustomer(id);
    }
    @GetMapping
    public List<CustomerResponse> getAll(){
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}/orders")
    public List<OrderResponse> getOrdersByCustomer(@PathVariable UUID id) {
        return orderService.getOrdersByCustomerId(id);
    }

}
