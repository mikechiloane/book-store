package com.mikechiloane.bookstore.web.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterUserRequest {
    private String username;
    private String password;
}
