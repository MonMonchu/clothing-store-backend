package com.clothingstore.mapper;

import com.clothingstore.dto.order.*;
import com.clothingstore.entity.*;

public class OrderMapper {

    public static Order toEntity(CreateOrderRequest dto,Customer customer){
        return new Order(customer, dto.paymentMethod(), dto.shippingAddress());
}

public static void updateOrder(Order order, UpdateOrderRequest dto){
    if (dto.shippingAddress() != null && !dto.shippingAddress().isBlank())
            order.setShippingAddress(dto.shippingAddress());

}

    public static OrderResponse toDto(Order order) {

        var items = order.getItems()
                .stream()
                .map(OrderItemMapper::toDto)
                .toList();

        return new OrderResponse(
                order.getId(),
                items,
                order.getTotalPrice(),
                order.getOrderStatus(),
                order.getPaymentStatus(),
                order.getPaymentMethod(),
                order.getShippingAddress(),
                order.getCreatedAt(),
                order.getUpdatedAt()
        );
    }
}
