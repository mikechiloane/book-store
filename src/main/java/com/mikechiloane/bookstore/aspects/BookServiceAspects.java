package com.mikechiloane.bookstore.aspects;

import com.mikechiloane.bookstore.exceptions.AlreadyExistsException;
import com.mikechiloane.bookstore.exceptions.NotFoundException;
import com.mikechiloane.bookstore.model.Book;
import com.mikechiloane.bookstore.util.BookUtil;
import com.mikechiloane.bookstore.util.InputValidator;
import com.mikechiloane.bookstore.util.UserUtil;
import com.mikechiloane.bookstore.web.request.AddBookRequest;
import com.mikechiloane.bookstore.web.request.UserBookRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class BookServiceAspects {
    private final BookUtil bookUtil;
    private final UserUtil userUtil;

    @Before("execution(* com.mikechiloane.bookstore.service.book.BookServiceImpl.saveBook(..)) && args(book)")
    public void beforeSaveBook(Book book){
        if(bookUtil.bookExists(book.getTitle())){
            throw new AlreadyExistsException("Book with title already exists");
        }
    }

    @Before("execution(* com.mikechiloane.bookstore.service.book.BookServiceImpl.deleteBook(..)) && args(id)")
    public void beforeDeleteBook(Long id){
        if(!bookUtil.bookExists(id)){
            throw new NotFoundException("Book does not exist");
        }
    }

    @Before("execution(* com.mikechiloane.bookstore.service.book.BookServiceImpl.getBookById(..)) && args(id)")
    public void beforeGetBookById(Long id){
        if(!bookUtil.bookExists(id)){
            throw new NotFoundException("Book does not exist");
        }
    }

    @Before(value = "execution(* com.mikechiloane.bookstore.service.book.BookServiceImpl.updateBook(..)) && args(id, book)", argNames = "id,book")
    public void beforeUpdateBook(Long id, AddBookRequest book){
        InputValidator.validateAddBookRequest(book);
        boolean existingBook = bookUtil.bookExists(id);
        if(!existingBook){
            throw new NotFoundException("Book does not exist");
        }
    }

    @Before("execution(* com.mikechiloane.bookstore.service.book.BookServiceImpl.saveBook(..)) && args(book)")
    public void beforeSaveBook(AddBookRequest book){
        InputValidator.validateAddBookRequest(book);
        if(bookUtil.bookExists(book.getTitle())){
            throw new AlreadyExistsException("Book with title already exists");
        }
    }

    @Before("execution(* com.mikechiloane.bookstore.service.book.BookServiceImpl.updateBook(..)) && args(book)")
    public void beforeUpdateBook(Book book){
        if(bookUtil.bookExists(book.getId())){
            throw new NotFoundException("Book does not exist");
        }
    }

    @Before("execution(* com.mikechiloane.bookstore.service.book.BookServiceImpl.getBooksByUsername(..)) && args(username)")
    public void beforeGetBooksByUsername(String username){
        if(!userUtil.userExists(username)){
            throw new NotFoundException("User does not exist");
        }
    }

    @Before("execution(* com.mikechiloane.bookstore.service.book.BookServiceImpl.getCheckedOutBooksForUser(..)) && args(username)")
    public void beforeGetCheckedOutBooksForUser(String username){
        if(!userUtil.userExists(username)){
            throw new NotFoundException("User does not exist");
        }
    }

    @Before("execution(* com.mikechiloane.bookstore.service.book.BookServiceImpl.checkoutBook(..)) && args(userBookRequest)")
    public void beforeCheckoutBook(UserBookRequest userBookRequest){
        InputValidator.validateUserBookRequest(userBookRequest);
        if(!userUtil.userExists(userBookRequest.getUsername())){
            throw new NotFoundException("User does not exist");
        }
        if(!bookUtil.bookExists(userBookRequest.getBookId())){
            throw new NotFoundException("Book does not exist");
        }
        if(!bookUtil.bookIsAvailable(userBookRequest.getBookId())){
            throw  new NotFoundException("Book is out of stock");
        }
    }

    @Before("execution(* com.mikechiloane.bookstore.service.book.BookServiceImpl.addBookToUserCart(..)) && args(userBookRequest)")
    public void beforeAddBookToUserCart(UserBookRequest userBookRequest){
        InputValidator.validateUserBookRequest(userBookRequest);
        if(!userUtil.userExists(userBookRequest.getUsername())){
            throw new NotFoundException("User does not exist");
        }
        if(!bookUtil.bookExists(userBookRequest.getBookId())){
            throw new NotFoundException("Book does not exist");
        }
        if(!bookUtil.bookIsAvailable(userBookRequest.getBookId())){
            throw  new NotFoundException("Book is out of stock");
        }
    }


}
