package com.clothingstore.mapper;


import com.clothingstore.dto.product.*;
import com.clothingstore.entity.*;

public class ProductMapper {

    public static Product toEntity(CreateProductRequest dto, Designer designer) {
        return new Product(designer,dto.name(),dto.description(),dto.price());

    }

    public static void updateProduct(Product product, UpdateProductRequest dto) {
        if (dto.name() != null && !dto.name().isBlank()){
            product.setName(dto.name());
        }
        if (dto.description() != null && !dto.description().isBlank()){
            product.setDescription(dto.description());
        }
        if (dto.price() != null){
            product.setPrice(dto.price());
        }


    }

    public static ProductResponse toDto(Product product) {
        Designer d = product.getDesigner();
        return new ProductResponse(product.getId(),
                d.getId(),
                d.getBrand(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.isActive());
    }


}
