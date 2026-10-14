package com.clothingstore.dto.productvariant;

import com.clothingstore.entity.Product;
import com.clothingstore.entity.ProductVariant;
import jakarta.validation.constraints.*;


public record CreateProductVariantRequest(
        @NotBlank
        @Size(max = 50)
        String color,


        @NotBlank
        @Size(max = 20)
        String size,


        @Positive
        int stock){
}


