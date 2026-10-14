package com.clothingstore.dto.product;

import java.math.BigDecimal;
import jakarta.validation.constraints.*;




public record CreateProductRequest(
        @NotBlank
        @Size(max = 255)
        String name,

        @Size(max = 2000)
        String description,

        @NotNull
        @Positive
        BigDecimal price){

}





