package com.mikechiloane.bookstore.aspects;

import com.mikechiloane.bookstore.exceptions.AlreadyExistsException;
import com.mikechiloane.bookstore.exceptions.NotFoundException;
import com.mikechiloane.bookstore.model.Book;
import com.mikechiloane.bookstore.util.BookUtil;
import com.mikechiloane.bookstore.util.InputValidator;
import com.mikechiloane.bookstore.util.UserUtil;
import com.mikechiloane.bookstore.web.request.AddBookRequest;
import com.mikechiloane.bookstore.web.request.UserBookRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceAspectsTest {

    @Mock
    private BookUtil bookUtil;

    @Mock
    private UserUtil userUtil;

    @Mock
    private InputValidator inputValidator;

    @InjectMocks
    private BookServiceAspects bookServiceAspects;

    private Book book;
    private AddBookRequest addBookRequest;
    private UserBookRequest userBookRequest;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");

        addBookRequest = new AddBookRequest();
        addBookRequest.setTitle("Test Book");
        addBookRequest.setGenre("Test Genre");
        addBookRequest.setQuantity(10);
        addBookRequest.setAuthor("Test Author");
        userBookRequest = new UserBookRequest();
        userBookRequest.setUsername("testuser");
        userBookRequest.setBookId(1L);
    }

    @Test
    void beforeSaveBook_BookExists() {
        when(bookUtil.bookExists(anyString())).thenReturn(true);

        assertThrows(AlreadyExistsException.class, () -> bookServiceAspects.beforeSaveBook(addBookRequest));
    }

    @Test
    void beforeDeleteBook_BookDoesNotExist() {
        when(bookUtil.bookExists(anyLong())).thenReturn(false);

        assertThrows(NotFoundException.class, () -> bookServiceAspects.beforeDeleteBook(1L));
    }

    @Test
    void beforeGetBookById_BookDoesNotExist() {
        when(bookUtil.bookExists(anyLong())).thenReturn(false);

        assertThrows(NotFoundException.class, () -> bookServiceAspects.beforeGetBookById(1L));
    }

    @Test
    void beforeUpdateBook_BookDoesNotExist() {
        when(bookUtil.bookExists(anyLong())).thenReturn(false);

        assertThrows(NotFoundException.class, () -> bookServiceAspects.beforeUpdateBook(1L, addBookRequest));
    }

    @Test
    void beforeGetBooksByUsername_UserDoesNotExist() {
        when(userUtil.userExists(anyString())).thenReturn(false);

        assertThrows(NotFoundException.class, () -> bookServiceAspects.beforeGetBooksByUsername("testuser"));
    }

    @Test
    void beforeGetCheckedOutBooksForUser_UserDoesNotExist() {
        when(userUtil.userExists(anyString())).thenReturn(false);

        assertThrows(NotFoundException.class, () -> bookServiceAspects.beforeGetCheckedOutBooksForUser("testuser"));
    }

    @Test
    void beforeCheckoutBook_UserDoesNotExist() {
        when(userUtil.userExists(anyString())).thenReturn(false);

        assertThrows(NotFoundException.class, () -> bookServiceAspects.beforeCheckoutBook(userBookRequest));
    }

    @Test
    void beforeCheckoutBook_BookDoesNotExist() {
        when(userUtil.userExists(anyString())).thenReturn(true);
        when(bookUtil.bookExists(anyLong())).thenReturn(false);

        assertThrows(NotFoundException.class, () -> bookServiceAspects.beforeCheckoutBook(userBookRequest));
    }

    @Test
    void beforeCheckoutBook_BookNotAvailable() {
        when(userUtil.userExists(anyString())).thenReturn(true);
        assertThrows(NotFoundException.class, () -> bookServiceAspects.beforeCheckoutBook(userBookRequest));
    }

    @Test
    void beforeAddBookToUserCart_UserDoesNotExist() {
        when(userUtil.userExists(anyString())).thenReturn(false);
        assertThrows(NotFoundException.class, () -> bookServiceAspects.beforeAddBookToUserCart(userBookRequest));
    }

    @Test
    void beforeAddBookToUserCart_BookDoesNotExist() {
        when(userUtil.userExists(anyString())).thenReturn(true);
        when(bookUtil.bookExists(anyLong())).thenReturn(false);

        assertThrows(NotFoundException.class, () -> bookServiceAspects.beforeAddBookToUserCart(userBookRequest));
    }

    @Test
    void beforeAddBookToUserCart_BookNotAvailable() {
        when(userUtil.userExists(anyString())).thenReturn(true);
        when(bookUtil.bookExists(anyLong())).thenReturn(true);
        when(bookUtil.bookIsAvailable(anyLong())).thenReturn(false);

        assertThrows(NotFoundException.class, () -> bookServiceAspects.beforeAddBookToUserCart(userBookRequest));
    }
}
