package com.mikechiloane.bookstore.util;

import com.mikechiloane.bookstore.model.User;
import com.mikechiloane.bookstore.repository.UserRepository;
import com.mikechiloane.bookstore.web.request.RegisterUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUtil {

    private final UserRepository userRepository;

    public boolean userExists(String username) {
        return userRepository.findByUsernameIgnoreCase(username).isPresent();
    }

    public boolean userExists(Long id) {
        return userRepository.findById(id).isPresent();
    }

    public static User registerUserToUser(RegisterUserRequest user) {
        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }


}
