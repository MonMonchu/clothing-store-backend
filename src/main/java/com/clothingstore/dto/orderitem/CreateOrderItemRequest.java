package com.clothingstore.dto.orderitem;

import java.util.UUID;
import jakarta.validation.constraints.*;

public record CreateOrderItemRequest (
        @NotNull
        UUID productVariantId,

        @Positive
        int quantity){
}
