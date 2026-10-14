package com.clothingstore.controller;

import com.clothingstore.dto.product.*;
import com.clothingstore.dto.productvariant.*;
import com.clothingstore.service.ProductService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }



    @PostMapping("/designers/{designerId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse addProduct(
            @Valid @RequestBody CreateProductRequest dto,
            @PathVariable UUID designerId
    ) {
        return productService.addProduct(dto, designerId);
    }

    @PatchMapping("/{id}")
    public ProductResponse update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateProductRequest dto
    ) {
        return productService.updateProduct(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        productService.deleteProduct(id);
    }


    @PostMapping("/{productId}/variants")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductVariantAdminResponse addVariant(
            @PathVariable UUID productId,
            @Valid @RequestBody CreateProductVariantRequest dto
    ) {
        return productService.addProductVariant(productId, dto);
    }

    @PatchMapping("/{productId}/variants/{variantId}")
    public ProductVariantAdminResponse updateVariant(
            @PathVariable UUID productId,
            @PathVariable UUID variantId,
            @Valid @RequestBody UpdateProductVariantRequest dto
    ) {
        return productService.updateProductVariant(productId, variantId, dto);
    }

    @DeleteMapping("/{productId}/variants/{variantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVariant(
            @PathVariable UUID productId,
            @PathVariable UUID variantId
    ) {
        productService.deleteProductVariant(productId, variantId);
    }

    @GetMapping
    public List<ProductResponse> getAll() {
        return productService.getAllActiveProducts();
    }

    @GetMapping("/{id}")
    public ProductResponse getById(@PathVariable UUID id) {
        return productService.getProductById(id);
    }

    @GetMapping("/designers/{designerId}")
    public List<ProductResponse> getAllByDesigner(
            @PathVariable UUID designerId
    ) {
        return productService.getActiveProductsByDesignerId(designerId);
    }

    @GetMapping("/{productId}/variants")
    public List<ProductVariantAdminResponse> getVariants(
            @PathVariable UUID productId
    ) {
        return productService.getVariantsByProductId(productId);
    }



    @GetMapping("/admin")
    public List<ProductResponse> getAllAdmin() {
        return productService.getAllProducts();
    }

    @GetMapping("/admin/designers/{designerId}")
    public List<ProductResponse> getByDesignerAdmin(
            @PathVariable UUID designerId
    ) {
        return productService.getProductsByDesignerId(designerId);
    }
}