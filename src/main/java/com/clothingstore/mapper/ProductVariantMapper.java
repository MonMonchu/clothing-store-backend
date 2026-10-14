package com.clothingstore.mapper;

import com.clothingstore.dto.productvariant.*;
import com.clothingstore.entity.Product;
import com.clothingstore.entity.ProductVariant;

public class ProductVariantMapper {

    public static ProductVariant toEntity(CreateProductVariantRequest dto, Product product) {

        return new ProductVariant(product,dto.color(), dto.size(), dto.stock());
    }

    public static void updateProductVariant(ProductVariant productVariant, UpdateProductVariantRequest dto) {
        if (dto.color() != null && !dto.color().isBlank()){
            productVariant.setColor(dto.color());
        }
        if (dto.size() != null && !dto.size().isBlank()){
            productVariant.setSize(dto.size());
        }

    }

    public static ProductVariantAdminResponse toDto(ProductVariant productVariant) {
        return new ProductVariantAdminResponse(productVariant.getId(), productVariant.getColor(),  productVariant.getSize(),
                productVariant.getStock(), productVariant.isActive(), productVariant.getCreatedAt(),
                productVariant.getUpdatedAt());
    }
}
