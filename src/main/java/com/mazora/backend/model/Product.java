package com.mazora.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message = "Produt name cannot be blank")
	//@Pattern(regexp = "^[A-Za-z ]+$", message = "Produt name must contain only letters and spaces")
	private String title;
	@NotBlank(message = "Description cannot be blank")
	//@Pattern(regexp = "^[A-Za-z ]+$", message = "Description must contain only letters and spaces")
	private String description;
	@DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
	private double price;
	private String imageUrl;
	private String category;

}
