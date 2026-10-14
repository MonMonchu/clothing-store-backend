package com.clothingstore.dto.productvariant;

import java.time.LocalDateTime;
import java.util.UUID;

public record ProductVariantAdminResponse(
        UUID id,
        String color,
        String size,
        int stock,
        boolean isActive,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {

}
