package com.spring.service.user.impl;

import com.spring.exceptions.ServiceException;
import com.spring.repository.UserDao;
import com.spring.service.user.UserServiceInterface;
import com.spring.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class UserService implements UserServiceInterface {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
        logger.info("UserService initialized");
    }

    @Override
    public User createUser(String login) {
        logger.info("Attempting to create user with login: {}", login);
        if (login == null || login.trim().isEmpty()) {
            logger.error("Attempt to create user with null or empty login");
            throw new ServiceException("User login cannot be null or empty");
        }

        String trimmedLogin = login.trim();

        Optional<User> existingUser = userDao.findByLogin(trimmedLogin);
        if (existingUser.isPresent()) {
            logger.warn("Attempt to create user with existing login: {}", trimmedLogin);
            throw new ServiceException("User with login " + trimmedLogin + " already exists");
        }

        User newUser = User.builder()
                .login(trimmedLogin)
                .build();
        User savedUser = userDao.save(newUser);
        logger.info("User created successfully with ID: {}", savedUser.getId());
        return savedUser;
    }

    @Override
    public User getUserById(Long id) {
        logger.info("Attempting to get user by ID: {}", id);
        if (id == null) {
            logger.error("Attempt to get user with null ID");
            throw new ServiceException("User ID cannot be null");
        }
        User user = userDao.findById(id);
        if (user == null) {
            logger.warn("User not found for ID: {}", id);
        } else {
            logger.info("User found: {}", user);
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        logger.info("Attempting to get all users");
        List<User> users = userDao.findAll();
        if (users.isEmpty()) {
            logger.warn("No users found in the database");
            throw new ServiceException("No users found");
        }
        logger.info("Retrieved {} users", users.size());
        return users;
    }
}
