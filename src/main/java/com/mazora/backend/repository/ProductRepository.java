package com.mazora.backend.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mazora.backend.model.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(String category);
}