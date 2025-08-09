package com.mazoraapp.dto;


import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String title;
    private String description;
    private double price;
    private String image;
    private String category;
}
