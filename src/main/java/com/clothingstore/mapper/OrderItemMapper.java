package com.clothingstore.mapper;


import com.clothingstore.dto.order.*;
import com.clothingstore.dto.orderitem.*;
import com.clothingstore.entity.*;

public class OrderItemMapper {

    public static OrderItem toEntity(
            CreateOrderItemRequest dto,
            ProductVariant productVariant) {

        return new OrderItem(
                productVariant,
                dto.quantity()
        );
    }
    public static OrderItemResponse toDto(OrderItem orderItem) {
        return new OrderItemResponse(
                orderItem.getId(),
                orderItem.getProductVariant().getId(),
                orderItem.getPriceAtPurchase(),
                orderItem.getQuantity()
        );
    }

            public static void updateEntity(
                    OrderItem orderItem,
            UpdateOrderItemRequest dto) {

        if (dto.increase() != null) {
            orderItem.increaseQuantity(dto.increase());
        }

        if (dto.decrease() != null) {
            orderItem.decreaseQuantity(dto.decrease());
        }
    }
}