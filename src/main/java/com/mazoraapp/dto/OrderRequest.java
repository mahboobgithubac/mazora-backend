package com.mazoraapp.dto;
import java.util.List;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class OrderRequest {
	@NotNull(message = "User ID is required")
	@Min(value = 1, message = "User ID must be greater than 0")
	private Long userId;
	@NotBlank(message = "Shipping address cannot be blank")
	private String shippingAddress;
	@NotEmpty(message = "Order must contain at least one item")
	private List<@Valid OrderItemRequest> items;
}
