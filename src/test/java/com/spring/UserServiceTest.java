package com.spring;

import com.spring.entity.User;
import com.spring.exceptions.ServiceException;
import com.spring.repository.UserDao;
import com.spring.service.user.impl.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserDao userDao;

    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userDao);
    }

    @Test
    void createUser_ValidLogin_ReturnsNewUser() {
        String login = "testUser";
        User newUser = User.builder()
                .login(login)
                .build();
        when(userDao.findByLogin(login)).thenReturn(Optional.empty());
        when(userDao.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = userService.createUser(login);

        assertNotNull(result);
        assertEquals(login, result.getLogin());
        verify(userDao).save(any(User.class));
    }

    @Test
    void createUser_ExistingLogin_ThrowsServiceException() {
        String login = "existingUser";
        User existingUser = User.builder()
                .login(login)
                .build();
        when(userDao.findByLogin(login)).thenReturn(Optional.of(existingUser));

        assertThrows(ServiceException.class, () -> userService.createUser(login));
    }

    @Test
    void createUser_NullLogin_ThrowsServiceException() {
        assertThrows(ServiceException.class, () -> userService.createUser(null));
    }

    @Test
    void getUserById_ValidId_ReturnsUser() {
        Long userId = 1L;
        User user = User.builder()
                .id(userId)
                .login("testUser")
                .build();
        when(userDao.findById(userId)).thenReturn(user);

        User result = userService.getUserById(userId);

        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals("testUser", result.getLogin());
    }

    @Test
    void getUserById_NullId_ThrowsServiceException() {
        assertThrows(ServiceException.class, () -> userService.getUserById(null));
    }

    @Test
    void getAllUsers_UsersExist_ReturnsList() {
        List<User> users = Arrays.asList(
                User.builder().id(1L).login("user1").build(),
                User.builder().id(2L).login("user2").build()
        );
        when(userDao.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("user1", result.get(0).getLogin());
        assertEquals("user2", result.get(1).getLogin());
    }

    @Test
    void getAllUsers_NoUsers_ThrowsServiceException() {
        when(userDao.findAll()).thenReturn(Arrays.asList());

        assertThrows(ServiceException.class, () -> userService.getAllUsers());
    }
}
