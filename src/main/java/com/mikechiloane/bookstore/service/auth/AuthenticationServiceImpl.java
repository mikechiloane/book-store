package com.mikechiloane.bookstore.service.auth;

import com.mikechiloane.bookstore.exceptions.AlreadyExistsException;
import com.mikechiloane.bookstore.exceptions.NotFoundException;
import com.mikechiloane.bookstore.model.User;
import com.mikechiloane.bookstore.repository.UserRepository;
import com.mikechiloane.bookstore.service.user.UserService;
import com.mikechiloane.bookstore.util.JWTUtil;
import com.mikechiloane.bookstore.util.UserUtil;
import com.mikechiloane.bookstore.web.request.LoginRequest;
import com.mikechiloane.bookstore.web.request.RegisterUserRequest;
import com.mikechiloane.bookstore.web.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JWTUtil jwtUtil;


    @Override
    public LoginResponse loginUser(LoginRequest loginRequest) {
        User user = userRepository.findByUsernameIgnoreCase(loginRequest.getUsername())
                .orElseThrow(() -> new NotFoundException("User not found"));
        if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return LoginResponse.builder().token(jwtUtil.createToken(user)).message("Login successful").build();
        }
        throw new NotFoundException("User not found");
    }

    @Override
    public void registerUser(RegisterUserRequest registerUserRequest) {
        User user = UserUtil.registerUserToUser(registerUserRequest);
        user.setPassword(passwordEncoder.encode(registerUserRequest.getPassword()));
        userService.saveUser(user);
    }

}
