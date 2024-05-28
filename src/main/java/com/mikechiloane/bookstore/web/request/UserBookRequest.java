package com.mikechiloane.bookstore.web.request;

import lombok.Data;

@Data
public class UserBookRequest {
    private Long bookId;
    private String username;
}
