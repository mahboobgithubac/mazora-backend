package com.mazora.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.mazora.backend.dto.ProductDTO;
import com.mazora.backend.exception.ProductNotFoundException;
import com.mazora.backend.model.Product;
import com.mazora.backend.repository.ProductRepository;
import com.mazora.backend.service.ProductService;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepo;

	@Value("${file.upload-dir}")
	//private final  Path uploadDir;
	private String uploadDir;
	
	@Value("${app.upload.path}")
	private String uploadPath;
	
	   private final Path uploadDir2 = Paths.get("uploads");
	   
	 @Autowired
	   FileStorageServiceImpl filestorage; 
	    
    
	 String imagePathProd="https://mazora-backend-production.up.railway.app/uploads/";
//	 https://<your-railway-app>.up.railway.app/uploads/<filename>
	 String imagePathLocal="http://localhost:8080/uploads/";

	public Product saveProductWithImage(String title, String description, double price, String category,
			MultipartFile imageFile) throws IOException {

// 1. Save file to disk
		//String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
//		Path filePath = Paths.get(uploadDir, fileName);
//		Files.createDirectories(filePath.getParent());
//		Files.write(filePath, imageFile.getBytes());
		String fileName =filestorage.saveFile(imageFile);
// 2. Save product
		Product product = new Product();
		product.setTitle(title);
		product.setDescription(description);
		product.setPrice(price);
		product.setCategory(category);
	//	product.setImageUrl("/uploads/" + fileName); // or full URL
		product.setImageUrl(fileName); // or full URL
//System.out.println("fileName ->"+fileName);
//System.out.println("product.getImageUrl(fileName);  ->"+product.getImageUrl() );
System.out.println("at  64 product====>"+product);

		return productRepo.save(product);
	}

	@Override
	public Product saveProduct(Product product) {
		return productRepo.save(product);
	}

	@Override
	public List<ProductDTO> getAllProducts() {
		//System.out.println("uploadPath in product service impl ->"+uploadPath);
		List<Product> products = productRepo.findAll();
	
		return products.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	private ProductDTO convertToDTO(Product product) {
		ProductDTO dto = new ProductDTO();
		dto.setId(product.getId());
		dto.setTitle(product.getTitle());
		dto.setDescription(product.getDescription());
		dto.setPrice(product.getPrice());
		//dto.setImage(product.getImageUrl());
		//for prod
		//dto.setImage(imagePathProd+""+ product.getImageUrl());
		//System.out.println("dto.getImage()->"+dto.getImage());
		//for local
//		dto.setImage(imagePathLocal+""+ product.getImageUrl());
		dto.setImage( product.getImageUrl());
	//	
	//	System.out.println("dto.getImage()->"+dto.getImage());
		dto.setCategory(product.getCategory());
		return dto;
	}

	@Override
	public ProductDTO getProductById(Long id) throws ProductNotFoundException {
		Product product = productRepo.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));
		return convertToDTO(product);
	}

	@Override
	public List<Product> getProductsByCategory(String category) {
		return productRepo.findByCategory(category);
	}

	@Override
	public void deleteProduct(Long id) throws ProductNotFoundException {

		Product product = productRepo.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));
		productRepo.deleteById(id);
	}

	@Override
	public List<Product> saveAll(List<Product> products) {
		return productRepo.saveAll(products);
	}

	@Override
		public void deleteAllProducts() {
		    productRepo.deleteAll();
		}
	}
	
