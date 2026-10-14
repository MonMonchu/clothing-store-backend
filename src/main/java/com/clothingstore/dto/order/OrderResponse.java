package com.clothingstore.dto.order;

import com.clothingstore.entity.*;
import com.clothingstore.dto.orderitem.OrderItemResponse;

import java.math.BigDecimal;
import java.util.List;

import java.time.LocalDateTime;
import java.util.UUID;

public record OrderResponse(    UUID id,
                                List<OrderItemResponse> items,
                                BigDecimal totalPrice,
                                OrderStatus orderStatus,
                                PaymentStatus paymentStatus,
                                PaymentMethod paymentMethod,
                                String shippingAddress,
                                LocalDateTime createdAt,
                                LocalDateTime updatedAt){
}
