package com.clothingstore.dto.productvariant;

import jakarta.validation.constraints.*;

public record UpdateProductVariantRequest(
        @Size(max = 50)
        String color,

        @Size(max = 20)
        String size
) {
}
