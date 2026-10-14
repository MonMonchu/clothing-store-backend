package com.clothingstore.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;


@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "designer_id", nullable = false)
    private Designer designer;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<ProductVariant> variants = new ArrayList<>();

    @Column(nullable = false)
    private String name;


    @Column(length = 2000)
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    private boolean active = true;


    @Column( updatable = false)
    @org.hibernate.annotations.CreationTimestamp
    private LocalDateTime createdAt;


    @org.hibernate.annotations.UpdateTimestamp
    private LocalDateTime updatedAt;


    protected Product() {
    }


    public Product(Designer designer, String name,String description, BigDecimal price) {
        this.name= name;
        this.description = description;
        this.price = price;
        designer.addProduct(this);

    }

    void setDesigner(Designer designer){
        this.designer = designer;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description){
      this.description = description;
    }

    public void setPrice(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        this.price = price;
    }

    public List<ProductVariant> getVariants() {
        return Collections.unmodifiableList(variants);
    }


    public UUID getId() {
        return id;
    }

    public Designer getDesigner() {
        return designer;
    }


    public String getName() {
        return name;
    }

    public String getDescription(){
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public boolean isActive(){
        return active;
    }




    public void addProductVariant(ProductVariant productVariant) {
        if (productVariant == null) {
            throw new IllegalArgumentException("Variant cannot be null");
        }

        if (!variants.contains(productVariant)) {
            variants.add(productVariant);
        }
        productVariant.setProduct(this);
    }

    public void removeProductVariant(ProductVariant productVariant) {
        if (productVariant == null) {
            throw new IllegalArgumentException("Variant cannot be null");
        }

        variants.remove(productVariant);
        productVariant.setProduct(null);
    }



    public void archive() {
        this.active = false;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product other = (Product) o;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}














