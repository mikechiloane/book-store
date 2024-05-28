package com.mikechiloane.bookstore.aspects;

import com.mikechiloane.bookstore.exceptions.AlreadyExistsException;
import com.mikechiloane.bookstore.exceptions.NotFoundException;
import com.mikechiloane.bookstore.model.User;
import com.mikechiloane.bookstore.util.UserUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceAspectTest {

    @Mock
    private UserUtil userUtil;

    @InjectMocks
    private UserServiceAspect userServiceAspect;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("testpassword");
    }

    @Test
    void beforeRegisterUser_UserExists() {
        when(userUtil.userExists(anyString())).thenReturn(true);

        assertThrows(AlreadyExistsException.class, () -> userServiceAspect.beforeRegisterUser(user));
    }

    @Test
    void beforeDeleteUserByUsername_UserDoesNotExist() {
        when(userUtil.userExists(anyString())).thenReturn(false);

        assertThrows(NotFoundException.class, () -> userServiceAspect.beforeDeleteUserByUsername("testuser"));
    }

    @Test
    void beforeDeleteUserById_UserDoesNotExist() {
        when(userUtil.userExists(anyLong())).thenReturn(false);

        assertThrows(NotFoundException.class, () -> userServiceAspect.beforeDeleteUserById(1L));
    }

    @Test
    void beforeGetUserByUsername_UserDoesNotExist() {
        when(userUtil.userExists(anyString())).thenReturn(false);

        assertThrows(NotFoundException.class, () -> userServiceAspect.beforeGetUserByUsername("testuser"));
    }

    @Test
    void beforeGetUserById_UserDoesNotExist() {
        when(userUtil.userExists(anyLong())).thenReturn(false);

        assertThrows(NotFoundException.class, () -> userServiceAspect.beforeGetUserById(1L));
    }

    @Test
    void beforeUpdateUser_UserDoesNotExist() {
        when(userUtil.userExists(anyLong())).thenReturn(false);

        assertThrows(NotFoundException.class, () -> userServiceAspect.beforeUpdateUser(user));
    }
}
