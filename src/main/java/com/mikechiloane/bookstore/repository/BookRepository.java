package com.mikechiloane.bookstore.repository;

import com.mikechiloane.bookstore.model.Book;
import com.mikechiloane.bookstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    boolean existsByTitleIgnoreCase(String title);
    List<Book> findByQuantityGreaterThan(int quantity);


    boolean existsByIdAndQuantityGreaterThan(Long id, int quantity);

}