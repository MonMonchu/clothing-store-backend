package com.clothingstore.dto.product;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record UpdateProductRequest(

        @Size(max = 225)
        String name,

        @Size(max = 2000)
        String description,

        @Positive
        BigDecimal price) {


}


