package com.mikechiloane.bookstore.repository;

import com.mikechiloane.bookstore.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUser_UsernameIgnoreCaseAndIsCompletedTrue(String username);
    List<Order> findByUser_UsernameIgnoreCaseAndIsCompletedFalse(String username);

}