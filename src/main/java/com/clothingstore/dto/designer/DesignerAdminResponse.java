package com.clothingstore.dto.designer;


import java.time.LocalDateTime;
import java.util.UUID;

public record DesignerAdminResponse(
        UUID id,
        String brand,
        String billingAddress,
        String email,
        String phoneNumber,
        String description,
        boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
                                             ){

}
