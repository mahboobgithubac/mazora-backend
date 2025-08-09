package com.mazora.backend.service;
import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.mazora.backend.dto.ProductDTO;
import com.mazora.backend.exception.ProductNotFoundException;
import com.mazora.backend.model.Product;

public interface ProductService {
    Product saveProduct(Product product);
    //Product saveProductWithImage(Product product);
    
    List<Product> saveAll(List<Product> products) ;
    List<ProductDTO> getAllProducts();
    ProductDTO getProductById(Long id) throws ProductNotFoundException;
    List<Product> getProductsByCategory(String category);
    void deleteProduct(Long id)throws ProductNotFoundException;
	Product saveProductWithImage(String title, String description, double price, String category,
			MultipartFile imageFile) throws IOException ;

	void deleteAllProducts();
}