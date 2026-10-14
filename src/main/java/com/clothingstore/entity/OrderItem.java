package com.clothingstore.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_variant_id", nullable = false)
    private ProductVariant productVariant;


    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal priceAtPurchase;

    @Column(nullable = false)
    private int quantity;



    protected OrderItem() {
    }

    public OrderItem(ProductVariant productVariant, int quantity) {
        if (productVariant == null) {
            throw new IllegalArgumentException("Product variant cannot be null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        this.productVariant = productVariant;
        this.quantity = quantity;
        this.priceAtPurchase = productVariant.getProduct().getPrice();
    }
     void setOrder(Order order) {
        this.order = order;
    }
    public void increaseQuantity(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        this.quantity += amount;
    }
    public void decreaseQuantity(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        if (amount > this.quantity) {
            throw new IllegalArgumentException("Cannot reduce below zero");
        }

        this.quantity -= amount;
    }



    public UUID getId() {
        return id;
    }

    public ProductVariant getProductVariant(){
        return productVariant;
    }

    public BigDecimal getPriceAtPurchase() {
        return priceAtPurchase;
    }


    public int getQuantity() {
        return quantity;
    }




}