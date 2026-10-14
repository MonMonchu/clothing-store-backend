package com.clothingstore.repository;

import com.clothingstore.entity.Product;
import com.clothingstore.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findByDesigner_Id(UUID designerId);
    List<Product> findByDesigner_IdAndActiveTrue(UUID designerId);
    List<Product> findByActiveTrue();

}
