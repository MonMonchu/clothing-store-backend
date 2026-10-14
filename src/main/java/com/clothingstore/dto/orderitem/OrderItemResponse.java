package com.clothingstore.dto.orderitem;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItemResponse (UUID id, UUID productVariantId, BigDecimal priceAtPurchase, int quantity){
}
