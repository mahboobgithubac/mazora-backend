package com.mazora.backend.dto;

import lombok.Data;

@Data
public class OrderItemDTO {
    private String productTitle;
    private double price;
    private int quantity;
    private String image; 
    
}