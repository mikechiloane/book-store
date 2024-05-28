package com.mikechiloane.bookstore.service.book;

import com.mikechiloane.bookstore.model.Book;
import com.mikechiloane.bookstore.web.request.AddBookRequest;
import com.mikechiloane.bookstore.web.request.UserBookRequest;
import com.mikechiloane.bookstore.web.response.OrderResponse;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();
    List<OrderResponse> getBooksByUsername(String username);
    List<OrderResponse> getCheckedOutBooksForUser(String username);
    List<Book> getAllAvailableBooks();
    Book getBookById(Long id);
    Book saveBook(AddBookRequest book);
    Book updateBook(Long id,  AddBookRequest book);
    void deleteBook(Long id);
    void checkoutBook(UserBookRequest userBookRequest);
    void addBookToUserCart(UserBookRequest userBookRequest);
}
