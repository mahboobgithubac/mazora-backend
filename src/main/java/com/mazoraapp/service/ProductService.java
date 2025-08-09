package com.mazoraapp.service;
import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.mazoraapp.dto.ProductDTO;
import com.mazoraapp.exception.ProductNotFoundException;
import com.mazoraapp.model.Product;

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