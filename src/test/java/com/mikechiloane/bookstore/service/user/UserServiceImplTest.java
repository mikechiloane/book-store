package com.mikechiloane.bookstore.service.user;

import com.mikechiloane.bookstore.exceptions.NotFoundException;
import com.mikechiloane.bookstore.model.User;
import com.mikechiloane.bookstore.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("testpassword");
    }

    @Test
    void saveUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.saveUser(user);

        assertNotNull(savedUser);
        assertEquals("testuser", savedUser.getUsername());
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void getUserById() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        User foundUser = userService.getUserById(1L);

        assertNotNull(foundUser);
        assertEquals("testuser", foundUser.getUsername());
    }

    @Test
    void getUserById_NotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.getUserById(1L));
    }

    @Test
    void getUserByUsername() {
        when(userRepository.findByUsernameIgnoreCase(anyString())).thenReturn(Optional.of(user));

        User foundUser = userService.getUserByUsername("testuser");

        assertNotNull(foundUser);
        assertEquals("testuser", foundUser.getUsername());
    }

    @Test
    void getUserByUsername_NotFound() {
        when(userRepository.findByUsernameIgnoreCase(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.getUserByUsername("testuser"));
    }

    @Test
    void deleteUserByUsername() {
        when(userRepository.findByUsernameIgnoreCase(anyString())).thenReturn(Optional.of(user));

        userService.deleteUserByUsername("testuser");

        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void deleteUserByUsername_NotFound() {
        when(userRepository.findByUsernameIgnoreCase(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.deleteUserByUsername("testuser"));
    }

    @Test
    void deleteUserById() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        userService.deleteUserById(1L);

        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void deleteUserById_NotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.deleteUserById(1L));
    }

    @Test
    void getAllUsers() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));

        List<User> users = userService.getAllUsers();

        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals("testuser", users.get(0).getUsername());
    }

    @Test
    void updateUser() {
        when(userRepository.findByUsernameIgnoreCase(anyString())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User updatedUser = userService.updateUser(user);

        assertNotNull(updatedUser);
        assertEquals("testuser", updatedUser.getUsername());
        assertEquals("testpassword", updatedUser.getPassword());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void loadUserByUsername() {
        when(userRepository.findByUsernameIgnoreCase(anyString())).thenReturn(Optional.of(user));

        UserDetails userDetails = userService.loadUserByUsername("testuser");

        assertNotNull(userDetails);
        assertEquals("testuser", userDetails.getUsername());
    }

    @Test
    void loadUserByUsername_NotFound() {
        when(userRepository.findByUsernameIgnoreCase(anyString())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("testuser"));
    }
}
