package com.clothingstore.dto.orderitem;

import jakarta.validation.constraints.Positive;

public record UpdateOrderItemRequest
        (@Positive
         Integer increase,

         @Positive
         Integer decrease) {
}
