package com.clothingstore.dto.order;

import com.clothingstore.dto.orderitem.CreateOrderItemRequest;
import com.clothingstore.entity.PaymentMethod;
import jakarta.validation.constraints.*;

import java.util.List;
import java.util.UUID;


public record CreateOrderRequest(
        @NotNull
        UUID customerId,

        @NotNull
        PaymentMethod paymentMethod,

        @NotBlank
        String shippingAddress,

        @NotNull
        @Size(min = 1)
        List<CreateOrderItemRequest> items) {
}


