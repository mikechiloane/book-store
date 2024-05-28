package com.mikechiloane.bookstore.repository;

import com.mikechiloane.bookstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByUsernameIgnoreCase(String username);
    List<User> findByUsernameIgnoreCaseAndPassword(String username, String password);
}
