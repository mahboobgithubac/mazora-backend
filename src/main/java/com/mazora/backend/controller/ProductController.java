package com.mazora.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.mazora.backend.dto.ProductDTO;
import com.mazora.backend.exception.ProductNotFoundException;
import com.mazora.backend.model.Product;
import com.mazora.backend.service.ProductService;

import jakarta.validation.Valid;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {

	@Autowired
	private ProductService productService;

	@PostMapping({ "", "/" })
	public Product addProduct(@Valid @RequestBody Product product) {
		return productService.saveProduct(product);
	}

	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Product> createProduct(@Valid @RequestParam("title") String title,
			@RequestParam("description") String description, @RequestParam("price") double price,
			@RequestParam("category") String category, @RequestParam("image") MultipartFile imageFile)
			throws IOException {

		Product product = productService.saveProductWithImage(title, description, price, category, imageFile);
		return ResponseEntity.ok(product);
	}

	@GetMapping("/uploads")
	public ResponseEntity<List<String>> listFiles() {
		File folder = new File("E:/SpringBoot Work/mazora-backend/mazora-backend/uploads/");
		String[] files = folder.list();
		return ResponseEntity.ok(Arrays.asList(files));
	}

	@PostMapping("/bulk")
	public List<Product> addProducts(@Valid @RequestBody List<Product> products) {
		return productService.saveAll(products);
	}

	@GetMapping({ "", "/" })
	public List<ProductDTO> getAllProducts() throws ProductNotFoundException {
		return productService.getAllProducts();
	}

	@GetMapping("/{id}")
	public ProductDTO getProductById(@PathVariable Long id) throws ProductNotFoundException {
		return productService.getProductById(id);
	}

	@GetMapping("/category/{category}")
	public List<Product> getByCategory(@PathVariable String category) {
		return productService.getProductsByCategory(category);
	}

	@DeleteMapping("/{id}")
	public void deleteProduct(@PathVariable Long id) throws ProductNotFoundException {
		productService.deleteProduct(id);
	}
}