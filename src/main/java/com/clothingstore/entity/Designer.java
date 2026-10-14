


package com.clothingstore.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.*;


@Entity
public class Designer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToMany(mappedBy = "designer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Product> products = new HashSet<>();

    @Column(nullable = false, length = 100)
    private String brand;

    @Column(length = 500, nullable = false)
    private String billingAddress;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(length = 2000)
    private String description;

    @Column(nullable = false)
    private boolean active = true;

    @Column(updatable = false)
    @org.hibernate.annotations.CreationTimestamp
    private LocalDateTime createdAt;

    @Column
    @org.hibernate.annotations.UpdateTimestamp
    private LocalDateTime updatedAt;




    protected Designer() {
    }


    public Designer(String brand, String billingAddress, String email, String phoneNumber, String description) {
        this.brand = brand;
        this.billingAddress = billingAddress;
        this.email = email != null ? email.toLowerCase() : null;
        this.phoneNumber = phoneNumber;
        this.description = description;
    }


    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public void setEmail(String email) {
        this.email = email != null ? email.toLowerCase() : null;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setDescription(String description){
        this.description = description;
    }


    public UUID getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }


    public String getBillingAddress() {
        return billingAddress;
    }


    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getDescription(){
        return description;
    }

    public Set<Product> getProducts() {
        return Collections.unmodifiableSet(products);
    }
    public boolean isActive() {
        return active;
    }


    public LocalDateTime getCreatedAt(){
        return createdAt;
    }
    public LocalDateTime getUpdatedAt(){
        return updatedAt;
    }



    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (products.contains(product)) {
            return;
        }
        products.add(product);
        product.setDesigner(this);
    }
    public void removeProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (!products.contains(product)) {
            return;
        }
        products.remove(product);
        product.setDesigner(null);
    }

    public void archive() {
        this.active = false;
    }
    public void activate() {
        this.active = true;
    }


    @Override
    public boolean equals(Object o){
        if (this == o)return true;
        if (!(o instanceof Designer)) return false;
        Designer other = (Designer) o;
        return id!= null && id.equals(other.id);
    }

    @Override
    public int hashCode(){
        return getClass().hashCode();
    }

}
