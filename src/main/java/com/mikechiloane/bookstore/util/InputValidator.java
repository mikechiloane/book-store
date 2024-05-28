package com.mikechiloane.bookstore.util;

import com.mikechiloane.bookstore.exceptions.MissingInputFieldException;
import com.mikechiloane.bookstore.web.request.AddBookRequest;
import com.mikechiloane.bookstore.web.request.LoginRequest;
import com.mikechiloane.bookstore.web.request.RegisterUserRequest;
import com.mikechiloane.bookstore.web.request.UserBookRequest;

public class InputValidator {
    public static void validateLoginRequest(LoginRequest loginRequest) {
        if (loginRequest.getUsername().isEmpty()) {
            throw new MissingInputFieldException("Username cannot be empty");
        }
        if (loginRequest.getPassword().isEmpty()) {
            throw new MissingInputFieldException("Password cannot be empty");
        }
    }

    public static void validateUserBookRequest(UserBookRequest userBookRequest) {
        if (userBookRequest.getUsername().isEmpty()) {
            throw new MissingInputFieldException("Username cannot be empty");
        }
        if (userBookRequest.getBookId() == 0) {
            throw new MissingInputFieldException("Book ID cannot be 0");
        }
    }

    public static void validateRegisterUserRequest(RegisterUserRequest registerUserRequest) {
        if (registerUserRequest.getUsername().isEmpty()) {
            throw new MissingInputFieldException("Username cannot be empty");
        }
        if (registerUserRequest.getPassword().isEmpty()) {
            throw new MissingInputFieldException("Password cannot be empty");
        }
    }


    public static void validateAddBookRequest(AddBookRequest addBookRequest) {
        if (addBookRequest.getTitle().isEmpty()) {
            throw new MissingInputFieldException("Title cannot be empty");
        }
        if (addBookRequest.getAuthor().isEmpty()) {
            throw new MissingInputFieldException("Author cannot be empty");
        }
        if (addBookRequest.getGenre().isEmpty()) {
            throw new MissingInputFieldException("Genre cannot be empty");
        }
        if (addBookRequest.getQuantity() == 0) {
            throw new MissingInputFieldException("Quantity cannot be 0");
        }
    }
}
