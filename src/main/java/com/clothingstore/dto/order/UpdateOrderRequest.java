package com.clothingstore.dto.order;
import jakarta.validation.constraints.Size;

public record UpdateOrderRequest (
        @Size(max = 500)
        String shippingAddress){
}
