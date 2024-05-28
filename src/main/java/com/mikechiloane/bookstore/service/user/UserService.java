package com.mikechiloane.bookstore.service.user;

import com.mikechiloane.bookstore.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    User saveUser(User user);
    User getUserById(Long id);
    User getUserByUsername(String username);
    void deleteUserByUsername(String username);
    void deleteUserById(Long id);
    List<User> getAllUsers();
    User updateUser(User user);
}
