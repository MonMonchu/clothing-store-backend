package com.clothingstore.dto.product;


import java.math.BigDecimal;
import java.util.UUID;

public record ProductResponse(UUID id, UUID designerId, String brand,String name, String description,
                              BigDecimal price, boolean active
        ) {

}


