package com.mikechiloane.bookstore.aspects;

import com.mikechiloane.bookstore.util.InputValidator;
import com.mikechiloane.bookstore.web.request.LoginRequest;
import com.mikechiloane.bookstore.web.request.RegisterUserRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuthServiceAspects {

    @Before("execution(* com.mikechiloane.bookstore.service.auth.AuthenticationServiceImpl.loginUser(..)) && args(login)")
    public void beforeUpdateBook(LoginRequest login) {
        InputValidator.validateLoginRequest(login);
    }

    @Before("execution(* com.mikechiloane.bookstore.service.auth.AuthenticationServiceImpl.registerUser(..)) && args(register)")
    public void beforeRegisterUser(RegisterUserRequest register) {
        InputValidator.validateRegisterUserRequest(register);
    }

}
