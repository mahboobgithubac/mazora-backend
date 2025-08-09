package com.mazora.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.mazora.backend.dto.ProductDTO;
import com.mazora.backend.exception.ProductNotFoundException;
import com.mazora.backend.model.Product;
import com.mazora.backend.service.ProductService;
import com.mazora.backend.service.impl.FileStorageServiceImpl;

import jakarta.validation.Valid;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/products")
//@CrossOrigin(origins = {"http://localhost:3000", "https://your-app.netlify.app"})
//@CrossOrigin(origins = "*")

public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	@Autowired
	private ProductService productService;
	@Autowired
	private FileStorageServiceImpl fileStorageService;
	

	@PostMapping({ "", "/" })
	public Product addProduct(@Valid @RequestBody Product product) {
		logger.info("Received request to add product: {}", product.getTitle());
		return productService.saveProduct(product);
	}

	@PostMapping({ "/withimage" })
	public ResponseEntity<Product> addProductWithImage(
			
		        @RequestParam("title") String title,
		        @RequestParam("description") String description,
		        @RequestParam("price") Double price,
		        @RequestParam("category") String category,
		        
		        @RequestParam("image") MultipartFile imageFile) throws IOException {

		    //String imagePath = fileStorageService.saveFile(imageFile); // save and return URL
		    Product product = productService.saveProductWithImage(title, description, price, category, imageFile);
		 
		    //productRepository.save(product);
//System.out.println("product        ->"+product);
		    return ResponseEntity.ok(product);
//		//}
	}
	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Product> createProduct(@Valid @RequestParam("title") String title,
			@RequestParam("description") String description, @RequestParam("price") double price,
			@RequestParam("category") String category, @RequestParam("image") MultipartFile imageFile)
			throws IOException {
        logger.info("Received product upload request: title={}, category={}, filename={}",
                title, category, imageFile.getOriginalFilename());
		Product product = productService.saveProductWithImage(title, description, price, category, imageFile);
		return ResponseEntity.ok(product);
	}
//   for checking existed files
//	@GetMapping("/uploads")
//	public ResponseEntity<List<String>> listFiles() {
//		File folder = new File("E:/SpringBoot Work/mazora-backend/mazora-backend/uploads/");
//		String[] files = folder.list();
//		return ResponseEntity.ok(Arrays.asList(files));
//	}

	@PostMapping("/bulk")
	public List<Product> addProducts(@Valid @RequestBody List<Product> products) {
		return productService.saveAll(products);
	}

	@GetMapping({ "", "/" })
	public List<ProductDTO> getAllProducts() throws ProductNotFoundException {
		
        logger.info("Received product fetched  request");
        return productService.getAllProducts();
	}

	@GetMapping("/{id}")
	public ProductDTO getProductById(@PathVariable Long id) throws ProductNotFoundException {
		  logger.info("Fetching product by id: {}", id);
		return productService.getProductById(id);
	}

	@GetMapping("/category/{category}")
	public List<Product> getByCategory(@PathVariable String category) {
		  logger.info("Fetching product by category: {}", category);
		return productService.getProductsByCategory(category);
	}

	@DeleteMapping("/{id}")
	public void deleteProduct(@PathVariable Long id) throws ProductNotFoundException {
        logger.info("Request to delete product with ID: {}", id);
		productService.deleteProduct(id);
	}
}