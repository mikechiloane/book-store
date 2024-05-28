package com.mikechiloane.bookstore.web.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddBookRequest {
    private String title;
    private String author;
    private String genre;
    private int quantity;
    private BigDecimal price;
}
