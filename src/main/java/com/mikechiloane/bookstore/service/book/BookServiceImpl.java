package com.mikechiloane.bookstore.service.book;

import com.mikechiloane.bookstore.model.Book;
import com.mikechiloane.bookstore.model.Order;
import com.mikechiloane.bookstore.model.User;
import com.mikechiloane.bookstore.repository.BookRepository;
import com.mikechiloane.bookstore.repository.OrderRepository;
import com.mikechiloane.bookstore.repository.UserRepository;
import com.mikechiloane.bookstore.util.BookUtil;
import com.mikechiloane.bookstore.web.request.AddBookRequest;
import com.mikechiloane.bookstore.web.request.UserBookRequest;
import com.mikechiloane.bookstore.web.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public List<OrderResponse> getBooksByUsername(String username) {
        List<Order> orders = orderRepository.findByUser_UsernameIgnoreCaseAndIsCompletedFalse(username);
        return BookUtil.mapToOrderResponse(orders);
    }

    @Override
    public List<OrderResponse> getCheckedOutBooksForUser(String username) {
        List<Order> orders = orderRepository.findByUser_UsernameIgnoreCaseAndIsCompletedTrue(username);
        return BookUtil.mapToOrderResponse(orders);
    }

    @Override
    public List<Book> getAllAvailableBooks() {
        return bookRepository.findByQuantityGreaterThan(1);
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public Book saveBook(AddBookRequest book) {
        Book newBook = Book.builder()
                .title(book.getTitle())
                .author(book.getAuthor())
                .quantity(book.getQuantity())
                .price(book.getPrice())
                .build();
        return bookRepository.save(newBook);
    }

    @Override
    public Book updateBook(Long id, AddBookRequest book) {
        Book bookToUpdate = bookRepository.findById(id).orElse(null);
        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setAuthor(book.getAuthor());
        bookToUpdate.setQuantity(book.getQuantity());
        bookToUpdate.setPrice(book.getPrice());
        return bookRepository.save(bookToUpdate);
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public void checkoutBook(UserBookRequest userBookRequest) {
        Book book = bookRepository.findById(userBookRequest.getBookId()).orElse(null);
        User user = userRepository.findByUsernameIgnoreCase(userBookRequest.getUsername()).orElse(null);
        book.setQuantity(book.getQuantity() - 1);
        bookRepository.save(book);
        Order order = Order.builder()
                .book(book)
                .user(user)
                .isCompleted(true)
                .build();

        orderRepository.save(order);
    }

    @Override
    public void addBookToUserCart(UserBookRequest userBookRequest) {
        User user = userRepository.findByUsernameIgnoreCase(userBookRequest.getUsername()).orElse(null);
        Book book = bookRepository.findById(userBookRequest.getBookId()).orElse(null);
        Order order = Order.builder()
                .book(book)
                .user(user)
                .isCompleted(false)
                .build();
        orderRepository.save(order);
    }


}
