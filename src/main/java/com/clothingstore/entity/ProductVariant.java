package com.clothingstore.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity
public class ProductVariant {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @OneToMany(mappedBy = "productVariant")
    private List<OrderItem> orderItems= new ArrayList<>();

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private String size;

    @Column(nullable = false)
    private int stock;

    @Column(nullable = false, updatable = false)
    @org.hibernate.annotations.CreationTimestamp
    private LocalDateTime createdAt;

    @Column
    @org.hibernate.annotations.UpdateTimestamp
    private LocalDateTime updatedAt;

    private boolean active = true;


    protected ProductVariant() {
    }

    public ProductVariant(Product product, String color, String size, int stock) {
        this.color = color;
        this.size = size;
        this.stock = stock;
        product.addProductVariant(this);
    }

     void setProduct(Product product) {
        this.product = product;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setSize(String size) {
        this.size = size;
    }
    public void setStock(int stock){
        if (stock < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }
        this.stock = stock;    }


    public List<OrderItem> getOrderItems(){
        return List.copyOf(orderItems);
    }


    public UUID getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public String getColor() {
        return color;
    }

    public String getSize() {
        return size;
    }

    public int getStock() {
        return stock;
    }


    public LocalDateTime getCreatedAt(){
        return createdAt;
    }
    public LocalDateTime getUpdatedAt(){
        return updatedAt;
    }

    public boolean isActive() {
        return active;
    }


    public void decreaseStock(int amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("Amount must be positive");

        if (amount > stock)
            throw new IllegalArgumentException("Not enough stock");

        stock -= amount;
    }

    public void increaseStock(int amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("Amount must be positive");

        stock += amount;
    }
    public void archive() {
        this.active = false;
    }


}
