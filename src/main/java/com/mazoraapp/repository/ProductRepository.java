package com.mazoraapp.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.mazoraapp.model.Product;
import java.util.List;
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(String category);
}