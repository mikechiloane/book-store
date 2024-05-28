package com.mikechiloane.bookstore.service.auth;

import com.mikechiloane.bookstore.web.request.LoginRequest;
import com.mikechiloane.bookstore.web.request.RegisterUserRequest;
import com.mikechiloane.bookstore.web.response.LoginResponse;

public interface AuthenticationService {

    LoginResponse loginUser(LoginRequest loginRequest);
    void registerUser(RegisterUserRequest registerUserRequest);
}
