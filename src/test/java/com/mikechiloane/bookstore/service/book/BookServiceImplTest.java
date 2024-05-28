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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookUtil bookUtil;

    private Book book;
    private User user;
    private Order order;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setQuantity(10);
        book.setPrice(new BigDecimal("100.0"));

        user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        order = new Order();
        order.setId(1L);
        order.setBook(book);
        order.setUser(user);
        order.setIsCompleted(false);
    }

    @Test
    void getAllBooks() {
        when(bookRepository.findAll()).thenReturn(Collections.singletonList(book));

        List<Book> books = bookService.getAllBooks();

        assertEquals(1, books.size());
        assertEquals(book, books.get(0));
    }

    @Test
    void getBooksByUsername() {
        when(orderRepository.findByUser_UsernameIgnoreCaseAndIsCompletedFalse(anyString())).thenReturn(Collections.singletonList(order));

        List<OrderResponse> orderResponses = bookService.getBooksByUsername("testuser");

        assertNotNull(orderResponses);
        assertEquals(1, orderResponses.size());
    }

    @Test
    void getCheckedOutBooksForUser() {
        order.setIsCompleted(true);
        when(orderRepository.findByUser_UsernameIgnoreCaseAndIsCompletedTrue(anyString())).thenReturn(Collections.singletonList(order));

        List<OrderResponse> orderResponses = bookService.getCheckedOutBooksForUser("testuser");

        assertNotNull(orderResponses);
        assertEquals(1, orderResponses.size());
    }

    @Test
    void getAllAvailableBooks() {
        when(bookRepository.findByQuantityGreaterThan(1)).thenReturn(Collections.singletonList(book));

        List<Book> availableBooks = bookService.getAllAvailableBooks();

        assertEquals(1, availableBooks.size());
        assertEquals(book, availableBooks.get(0));
    }

    @Test
    void getBookById() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));

        Book foundBook = bookService.getBookById(1L);

        assertNotNull(foundBook);
        assertEquals(book, foundBook);
    }

    @Test
    void saveBook() {
        AddBookRequest addBookRequest = new AddBookRequest();
        addBookRequest.setTitle("Test Book");
        addBookRequest.setAuthor("Test Author");
        addBookRequest.setQuantity(10);
        addBookRequest.setPrice(new BigDecimal("100.0"));

        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book savedBook = bookService.saveBook(addBookRequest);

        assertNotNull(savedBook);
        assertEquals(book, savedBook);
    }

    @Test
    void updateBook() {
        AddBookRequest addBookRequest = new AddBookRequest();
        addBookRequest.setTitle("Updated Book");
        addBookRequest.setAuthor("Updated Author");
        addBookRequest.setQuantity(20);
        addBookRequest.setPrice(new BigDecimal("150.0"));

        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book updatedBook = bookService.updateBook(1L, addBookRequest);

        assertNotNull(updatedBook);
        assertEquals("Updated Book", updatedBook.getTitle());
        assertEquals("Updated Author", updatedBook.getAuthor());
        assertEquals(20, updatedBook.getQuantity());
        assertEquals(new BigDecimal("150.0"), updatedBook.getPrice());
    }

    @Test
    void deleteBook() {
        bookService.deleteBook(1L);

        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    void checkoutBook() {
        UserBookRequest userBookRequest = new UserBookRequest();
        userBookRequest.setBookId(1L);
        userBookRequest.setUsername("testuser");

        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(userRepository.findByUsernameIgnoreCase(anyString())).thenReturn(Optional.of(user));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        bookService.checkoutBook(userBookRequest);

        assertEquals(9, book.getQuantity());
        verify(bookRepository, times(1)).save(book);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void addBookToUserCart() {
        UserBookRequest userBookRequest = new UserBookRequest();
        userBookRequest.setBookId(1L);
        userBookRequest.setUsername("testuser");

        when(userRepository.findByUsernameIgnoreCase(anyString())).thenReturn(Optional.of(user));
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        bookService.addBookToUserCart(userBookRequest);

        verify(orderRepository, times(1)).save(any(Order.class));
    }
}
