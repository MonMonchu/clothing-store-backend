package com.clothingstore.mapper;

import com.clothingstore.dto.designer.*;
import com.clothingstore.entity.Designer;



public class DesignerMapper {

    public static Designer toEntity(CreateDesignerRequest dto) {
         return new Designer(dto.brand(), dto.billingAddress(),dto.email(),dto.phoneNumber(), dto.description());
    }

    public static void updateEntity(
            Designer designer,
            UpdateDesignerRequest dto) {

        if (dto.brand() != null && !dto.brand().isBlank()) {
            designer.setBrand(dto.brand());
        }

        if (dto.billingAddress() != null &&
                !dto.billingAddress().isBlank()) {
            designer.setBillingAddress(dto.billingAddress());
        }

        if (dto.email() != null &&
                !dto.email().isBlank()) {
            designer.setEmail(dto.email());
        }

        if (dto.phoneNumber() != null &&
                !dto.phoneNumber().isBlank()) {
            designer.setPhoneNumber(dto.phoneNumber());
        }
        if (dto.description() != null) {
            designer.setDescription(dto.description());
        }
    }
    public static DesignerAdminResponse toAdminDto(Designer designer) {
        return new DesignerAdminResponse(
                designer.getId(),
                designer.getBrand(),
                designer.getBillingAddress(),
                designer.getEmail(),
                designer.getPhoneNumber(),
                designer.getDescription(),
                designer.isActive(),
                designer.getCreatedAt(),
                designer.getUpdatedAt()

                );
    }
    public static DesignerPublicResponse toDto(Designer designer) {
        return new DesignerPublicResponse(
                designer.getId(),
                designer.getBrand(),
                designer.getDescription()
        );
    }


}


