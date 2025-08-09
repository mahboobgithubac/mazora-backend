package com.mazoraapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.mazoraapp.dto.ProductDTO;
import com.mazoraapp.exception.ProductNotFoundException;
import com.mazoraapp.model.Product;
import com.mazoraapp.repository.ProductRepository;
import com.mazoraapp.service.ImageUploadService;
import com.mazoraapp.service.ProductService;
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
	// private final Path uploadDir;
	private String uploadDir;

	@Value("${app.upload.path}")
	private String uploadPath;

	private final Path uploadDir2 = Paths.get("uploads");

	@Autowired
	FileStorageServiceImpl filestorage;
	@Autowired
	ImageUploadServiceImpl imageUploadServiceImpl;
	String imagePathProd = "https://mazora-backend-production.up.railway.app/uploads/";
//	 https://<your-railway-app>.up.railway.app/uploads/<filename>
	String imagePathLocal = "http://localhost:8080/uploads/";

//	public Product saveProductWithImage(String title, String description, double price, String category,
//			MultipartFile imageFile) throws IOException {
//
//// 1. Save file to disk
//		//String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
////		Path filePath = Paths.get(uploadDir, fileName);
////		Files.createDirectories(filePath.getParent());
////		Files.write(filePath, imageFile.getBytes());
//		String fileName =filestorage.saveFile(imageFile);
//// 2. Save product
//		Product product = new Product();
//		product.setTitle(title);
//		product.setDescription(description);
//		product.setPrice(price);
//		product.setCategory(category);
//	//	product.setImageUrl("/uploads/" + fileName); // or full URL
//		product.setImageUrl(fileName); // or full URL
////System.out.println("fileName ->"+fileName);
////System.out.println("product.getImageUrl(fileName);  ->"+product.getImageUrl() );
//
//
//		return productRepo.save(product);
//	}

	public Product saveProductWithImage(String title, String description, double price, String category,
			MultipartFile imageFile) throws IOException {
		 
//		System.out.println("At 72 Inside save product with image*******");
//		System.out.println("title  ->"+title);
//		System.out.println("description   ->"+description);
//		System.out.println("price    ->"+price);
//		System.out.println("category    ->"+category);
//		System.out.println("imageFile.getName()  ->"+imageFile.getName());

		String imageUrl = imageUploadServiceImpl.uploadFile(imageFile);
			//System.out.println("imageUrl->"+imageUrl);
		Product product = new Product();
		product.setTitle(title);
		product.setPrice(price);
		product.setDescription(description);
		product.setImageUrl(imageUrl);
		product.setCategory(category);

		return productRepo.save(product);

	}

	@Override
	public Product saveProduct(Product product) {
		return productRepo.save(product);
	}

	@Override
	public List<ProductDTO> getAllProducts() {
		// System.out.println("uploadPath in product service impl ->"+uploadPath);
		List<Product> products = productRepo.findAll();

		return products.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	private ProductDTO convertToDTO(Product product) {
		ProductDTO dto = new ProductDTO();
		dto.setId(product.getId());
		dto.setTitle(product.getTitle());
		dto.setDescription(product.getDescription());
		dto.setPrice(product.getPrice());
		// dto.setImage(product.getImageUrl());
		// for prod
		// dto.setImage(imagePathProd+""+ product.getImageUrl());
		// System.out.println("dto.getImage()->"+dto.getImage());
		// for local
//		dto.setImage(imagePathLocal + "" + product.getImageUrl());
		dto.setImage(product.getImageUrl());
//		System.out.println("dto.getImage()->"+dto.getImage());
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