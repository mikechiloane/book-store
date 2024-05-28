package com.mikechiloane.bookstore.aspects;


import com.mikechiloane.bookstore.exceptions.AlreadyExistsException;
import com.mikechiloane.bookstore.exceptions.NotFoundException;
import com.mikechiloane.bookstore.model.User;
import com.mikechiloane.bookstore.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
@Aspect
@Component
@RequiredArgsConstructor
public class UserServiceAspect{

    private final UserUtil userUtil;



    @Before("execution(* com.mikechiloane.bookstore.service.user.UserServiceImpl.saveUser(..)) && args(user)")
    public void beforeRegisterUser(User user){
        if(userUtil.userExists(user.getUsername())){
            throw new AlreadyExistsException("User already exists");
        }
    }

    @Before("execution(* com.mikechiloane.bookstore.service.user.UserServiceImpl.deleteUserByUsername(..)) && args(username)")
    public void beforeDeleteUserByUsername(String username){
        if(!userUtil.userExists(username)){
            throw new NotFoundException("User does not exist");
        }
    }

    @Before("execution(* com.mikechiloane.bookstore.service.user.UserServiceImpl.deleteUserById(..)) && args(id)")
    public void beforeDeleteUserById(Long id){
        if(!userUtil.userExists(id)){
            throw new NotFoundException("User does not exist");
        }
    }

    @Before("execution(* com.mikechiloane.bookstore.service.user.UserServiceImpl.getUserByUsername(..)) && args(username)")
    public void beforeGetUserByUsername(String username){
        if(!userUtil.userExists(username)){
            throw new NotFoundException("User does not exist");
        }
    }

    @Before("execution(* com.mikechiloane.bookstore.service.user.UserServiceImpl.getUserById(..)) && args(id)")
    public void beforeGetUserById(Long id){
        if(!userUtil.userExists(id)){
            throw new NotFoundException("User does not exist");
        }
    }

    @Before("execution(* com.mikechiloane.bookstore.service.user.UserServiceImpl.updateUser(..)) && args(user)")
    public void beforeUpdateUser(User user){
        if(!userUtil.userExists(user.getId())){
            throw new NotFoundException("User does not exist");
        }
    }

}
