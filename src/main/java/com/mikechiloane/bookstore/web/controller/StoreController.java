package com.mikechiloane.bookstore.web.controller;


import com.mikechiloane.bookstore.model.Book;
import com.mikechiloane.bookstore.service.book.BookService;
import com.mikechiloane.bookstore.web.request.AddBookRequest;
import com.mikechiloane.bookstore.web.request.UserBookRequest;
import com.mikechiloane.bookstore.web.response.OrderResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/store")
@RestController
public class StoreController {

    private final BookService bookService;

    @PostMapping("/addToCart")
    public ResponseEntity<String> addBookToUserCart(@RequestBody UserBookRequest userBookRequest){
        bookService.addBookToUserCart(userBookRequest);
        return ResponseEntity.ok("Book added to cart");
    }

    @PostMapping("/addBook")
    public ResponseEntity<String> addBook(@RequestBody AddBookRequest addBookRequest){
        bookService.saveBook(addBookRequest);
        return ResponseEntity.ok("Book added");
    }

    @PostMapping("/checkout")
    public ResponseEntity<String> checkoutBook(@RequestBody UserBookRequest userBookRequest){
        bookService.checkoutBook(userBookRequest);
        return ResponseEntity.ok("Book checked out");
    }

    @GetMapping("/books/user/{username}")
    public ResponseEntity<List<OrderResponse>> getUserBooks(@PathVariable String username){
        return ResponseEntity.ok(bookService.getCheckedOutBooksForUser(username));
    }

    @GetMapping("/books/user/cart/{username}")
    public ResponseEntity<List<OrderResponse>> getUserCart(@PathVariable String username){
        return ResponseEntity.ok(bookService.getBooksByUsername(username));
    }

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAllBooks(){
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id){
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @GetMapping("/books/available")
    public ResponseEntity<List<Book>> getAvailableBooks(){
        return ResponseEntity.ok(bookService.getAllAvailableBooks());
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<String> updateBook(@PathVariable Long id, @RequestBody AddBookRequest addBookRequest){
        bookService.updateBook(id, addBookRequest);
        return ResponseEntity.ok("Book updated");
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<String> deleteBookById(@PathVariable Long id){
        bookService.deleteBook(id);
        return ResponseEntity.ok("Book deleted");
    }

}
