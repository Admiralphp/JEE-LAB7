package com.example.analytics.dto;

import lombok.Data;

@Data
public class Book {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private Double price;
    private Integer userId;
}
