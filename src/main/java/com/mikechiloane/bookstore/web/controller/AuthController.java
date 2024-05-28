package com.mikechiloane.bookstore.web.controller;

import com.mikechiloane.bookstore.service.auth.AuthenticationService;
import com.mikechiloane.bookstore.web.request.LoginRequest;
import com.mikechiloane.bookstore.web.request.RegisterUserRequest;
import com.mikechiloane.bookstore.web.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterUserRequest registerUserRequest){
        authenticationService.registerUser(registerUserRequest);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authenticationService.loginUser(loginRequest));
    }

}
