package com.example.coursework;

import com.example.coursework.model.User;
import com.example.coursework.repository.UserRepository;
import com.example.coursework.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void testSaveUser() {
        User user = new User();
        user.setId(1L);
        user.setUserName("user1");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPhone("1234567890");
        user.setEmail("johndoe@example.com");
        user.setPassword("password1");

        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userService.saveUser(user);

        assertEquals("user1", savedUser.getUserName());
        assertEquals("John", savedUser.getFirstName());
        assertEquals("Doe", savedUser.getLastName());
        assertEquals("1234567890", savedUser.getPhone());
        assertEquals("johndoe@example.com", savedUser.getEmail());
        assertEquals("password1", savedUser.getPassword());
    }

    @Test
    public void testFindByUserName() {
        User user = new User();
        user.setId(1L);
        user.setUserName("user1");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPhone("1234567890");
        user.setEmail("johndoe@example.com");
        user.setPassword("password1");

        when(userRepository.findByUserName("user1")).thenReturn(user);

        User foundUser = userService.findByUserName("user1");

        assertNotNull(foundUser);
        assertEquals(1L, foundUser.getId().longValue());
        assertEquals("user1", foundUser.getUserName());
        assertEquals("John", foundUser.getFirstName());
        assertEquals("Doe", foundUser.getLastName());
        assertEquals("1234567890", foundUser.getPhone());
        assertEquals("johndoe@example.com", foundUser.getEmail());
        assertEquals("password1", foundUser.getPassword());
    }
}
