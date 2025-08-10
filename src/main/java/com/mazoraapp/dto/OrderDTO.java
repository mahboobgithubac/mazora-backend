package com.mazoraapp.dto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
@Data
public class OrderDTO {
    private Long id;
    private String shippingAddress;
    private LocalDateTime orderDate;
    private String userName;          // Instead of entire User object
    private String email;          // Instead of entire User object
    
    private List<OrderItemDTO> items; // Nested DTO list
	private double totalAmount;

}