package com.clothingstore.controller;

import com.clothingstore.dto.order.CreateOrderRequest;
import com.clothingstore.dto.order.OrderResponse;
import com.clothingstore.dto.order.UpdateOrderRequest;
import com.clothingstore.service.OrderService;
import com.clothingstore.dto.orderitem.CreateOrderItemRequest;
import com.clothingstore.dto.orderitem.UpdateOrderItemRequest;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public OrderResponse createOrder(@Valid @RequestBody CreateOrderRequest dto) {
        return orderService.createOrder(dto);
    }

    @GetMapping("/{id}")
    public OrderResponse getById(@PathVariable UUID id){
        return orderService.getOrderById(id);
    }

    @GetMapping
    public List<OrderResponse> getAll() {
        return orderService.getAll();
    }

    @GetMapping("/customers/{customerId}")
    public List<OrderResponse> getByCustomer(@PathVariable UUID customerId) {
        return orderService.getOrdersByCustomerId(customerId);
    }

    @PutMapping("/{id}")
    public OrderResponse update(@PathVariable UUID id,
                                @RequestBody UpdateOrderRequest dto){
        return orderService.updateOrder(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id){
        orderService.deleteOrder(id);
    }
    @PostMapping("/{orderId}/items")
    public OrderResponse addItem(
            @PathVariable UUID orderId,
            @Valid @RequestBody CreateOrderItemRequest dto) {

        return orderService.addItem(orderId, dto);
    }

    @PatchMapping("/{orderId}/items/{itemId}")
    public OrderResponse updateItem(
            @PathVariable UUID orderId,
            @PathVariable UUID itemId,
            @Valid @RequestBody UpdateOrderItemRequest dto) {

        return orderService.updateItem(orderId, itemId, dto);
    }

    @DeleteMapping("/{orderId}/items/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeItem(
            @PathVariable UUID orderId,
            @PathVariable UUID itemId) {

        orderService.removeItem(orderId, itemId);
    }
    @PostMapping("/{orderId}/pay")
    public OrderResponse payOrder(@PathVariable UUID orderId){
        return orderService.processPayment(orderId);
    }
}