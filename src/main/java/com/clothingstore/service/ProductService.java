package com.clothingstore.service;

import com.clothingstore.dto.product.*;
import com.clothingstore.dto.productvariant.CreateProductVariantRequest;
import com.clothingstore.dto.productvariant.ProductVariantAdminResponse;
import com.clothingstore.dto.productvariant.UpdateProductVariantRequest;
import com.clothingstore.entity.Designer;
import com.clothingstore.entity.Product;
import com.clothingstore.entity.ProductVariant;
import com.clothingstore.mapper.ProductMapper;
import com.clothingstore.mapper.ProductVariantMapper;
import com.clothingstore.repository.DesignerRepository;
import com.clothingstore.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final DesignerRepository designerRepository;

    public ProductService(
            ProductRepository productRepository,
            DesignerRepository designerRepository
    ) {
        this.productRepository = productRepository;
        this.designerRepository = designerRepository;
    }


    @Transactional
    public ProductResponse addProduct(CreateProductRequest dto, UUID designerId) {

        Designer designer = designerRepository.findById(designerId)
                .orElseThrow(() -> new IllegalArgumentException("Designer not found"));

        Product product = ProductMapper.toEntity(dto, designer);
        Product saved = productRepository.save(product);

        return ProductMapper.toDto(saved);
    }

    public ProductResponse getProductById(UUID id) {

        Product product = getProductOrThrow(id);

        return ProductMapper.toDto(product);
    }

    @Transactional
    public ProductResponse updateProduct(UUID id, UpdateProductRequest dto) {

        Product product = getProductOrThrow(id);

        ProductMapper.updateProduct(product, dto);

        return ProductMapper.toDto(product);
    }

    @Transactional
    public void deleteProduct(UUID id) {

        Product product = getProductOrThrow(id);

        productRepository.delete(product);
    }

    @Transactional
    public void archiveProduct(UUID id) {

        Product product = getProductOrThrow(id);

        product.archive();
    }

    public List<ProductResponse> getProductsByDesignerId(UUID designerId) {

        return productRepository.findByDesigner_Id(designerId)
                .stream()
                .map(ProductMapper::toDto)
                .toList();
    }

    public List<ProductResponse> getActiveProductsByDesignerId(UUID designerId) {

        return productRepository.findByDesigner_IdAndActiveTrue(designerId)
                .stream()
                .map(ProductMapper::toDto)
                .toList();
    }

    public List<ProductResponse> getAllProducts() {

        return productRepository.findAll()
                .stream()
                .map(ProductMapper::toDto)
                .toList();
    }

    public List<ProductResponse> getAllActiveProducts() {

        return productRepository.findByActiveTrue()
                .stream()
                .map(ProductMapper::toDto)
                .toList();
    }



    @Transactional
    public ProductVariantAdminResponse addProductVariant(
            UUID productId,
            CreateProductVariantRequest dto
    ) {

        Product product = getProductOrThrow(productId);

        ProductVariant productVariant = ProductVariantMapper.toEntity(dto, product);

        productRepository.save(product);

        return ProductVariantMapper.toDto(productVariant);
    }

    public List<ProductVariantAdminResponse> getVariantsByProductId(UUID productId) {

        Product product = getProductOrThrow(productId);

        return product.getVariants()
                .stream()
                .map(ProductVariantMapper::toDto)
                .toList();
    }

    @Transactional
    public ProductVariantAdminResponse updateProductVariant(
            UUID productId,
            UUID variantId,
            UpdateProductVariantRequest dto
    ) {

        Product product = getProductOrThrow(productId);

        ProductVariant productVariant = product.getVariants()
                .stream()
                .filter(variant -> variant.getId().equals(variantId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Variant not found"));

        ProductVariantMapper.updateProductVariant(productVariant, dto);

        return ProductVariantMapper.toDto(productVariant);
    }

    @Transactional
    public void deleteProductVariant(UUID productId, UUID variantId) {

        Product product = getProductOrThrow(productId);

        ProductVariant productVariant = product.getVariants()
                .stream()
                .filter(variant -> variant.getId().equals(variantId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Variant not found"));

        product.removeProductVariant(productVariant);
    }

    private Product getProductOrThrow(UUID id) {

        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }
}