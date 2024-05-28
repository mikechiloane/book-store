package com.mikechiloane.bookstore.web.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderResponse {
    private Long id;
    private String title;
    private String author;
}
