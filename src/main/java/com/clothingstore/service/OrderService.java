package com.clothingstore.service;

import com.clothingstore.dto.order.CreateOrderRequest;
import com.clothingstore.dto.order.OrderResponse;
import com.clothingstore.dto.order.UpdateOrderRequest;
import com.clothingstore.dto.orderitem.CreateOrderItemRequest;
import com.clothingstore.dto.orderitem.UpdateOrderItemRequest;
import com.clothingstore.entity.*;
import com.clothingstore.mapper.OrderItemMapper;
import com.clothingstore.mapper.OrderMapper;
import com.clothingstore.repository.OrderRepository;
import com.clothingstore.repository.CustomerRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductVariantRepository productVariantRepository;


    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository, ProductVariantRepository productVariantRepository){
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productVariantRepository = productVariantRepository;

    }



    @Transactional
    public OrderResponse createOrder(CreateOrderRequest dto) {

        Customer customer = customerRepository.findById(dto.customerId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        Order order = OrderMapper.toEntity(dto, customer);

        Order saved = orderRepository.save(order);

        return OrderMapper.toDto(saved);
    }


    public OrderResponse getOrderById(UUID id){
        Order order = getOrderOrThrow(id);

        return OrderMapper.toDto(order);
    }

    @Transactional
    public OrderResponse updateOrder(UUID id, UpdateOrderRequest dto){
        Order order = getOrderOrThrow(id);

        OrderMapper.updateOrder(order, dto);

        return OrderMapper.toDto(order);
    }

    public void deleteOrder(UUID id){
        if (!orderRepository.existsById(id)){
            throw new IllegalArgumentException("Order not found");
        }
        orderRepository.deleteById(id);
    }


    public List<OrderResponse> getAll(){
        return orderRepository.findAll().stream().map(OrderMapper::toDto).toList();
    }

    public List<OrderResponse> getOrdersByCustomerId(UUID customerId) {
        return orderRepository.findByCustomerId(customerId)
                .stream()
                .map(OrderMapper::toDto)
                .toList();
    }

    @Transactional
    public OrderResponse addItem(UUID orderId, CreateOrderItemRequest dto) {

        Order order = getOrderOrThrow(orderId);
        ProductVariant variant = productVariantRepository.findById(dto.productVariantId())
                .orElseThrow(() -> new IllegalArgumentException("Product variant not found"));

        if (variant.getStock() < dto.quantity()) {
            throw new IllegalArgumentException("Not enough stock");
        }

        OrderItem item = OrderItemMapper.toEntity(dto, variant);

        order.addItem(item); // 💎 your domain logic

        variant.decreaseStock(dto.quantity());

        return OrderMapper.toDto(order);
    }
    @Transactional
    public OrderResponse updateItem(
            UUID orderId,
            UUID itemId,
            UpdateOrderItemRequest dto) {

        Order order = getOrderOrThrow(orderId);

        OrderItem item = order.getItems().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Order item not found"));

        if (dto.increase() != null) {
            item.increaseQuantity(dto.increase());
        }

        if (dto.decrease() != null) {
            item.decreaseQuantity(dto.decrease());
        }

        return OrderMapper.toDto(order);
    }
    @Transactional
    public OrderResponse removeItem(UUID orderId, UUID itemId) {

        Order order = getOrderOrThrow(orderId);
        OrderItem item = order.getItems().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Order item not found"));

        order.removeItem(item);

        return OrderMapper.toDto(order);
    }

    @Transactional
    public OrderResponse processPayment(UUID orderId){
        Order order =  getOrderOrThrow(orderId);
        if (order.getPaymentStatus() == PaymentStatus.PAID){
            throw new IllegalArgumentException("Order already paid");
        }
        boolean paymentSuccess = simulatePayment();

        if (!paymentSuccess){
            throw new IllegalArgumentException("Payment failed");
        }
        order.markAsPaid();
        return OrderMapper.toDto(order);
    }

    private boolean simulatePayment() {
        return Math.random() > 0.2;
    }

    public Order getOrderOrThrow(UUID id){
        return orderRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Order not found"));

    }

}
