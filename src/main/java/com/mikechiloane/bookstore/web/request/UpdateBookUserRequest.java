package com.mikechiloane.bookstore.web.request;


import lombok.Data;

@Data
public class UpdateBookUserRequest {
    private Long bookId;
    private Long userId;
}
