package com.mikechiloane.bookstore.web.response;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {

    private String token;
    private String message;

}
