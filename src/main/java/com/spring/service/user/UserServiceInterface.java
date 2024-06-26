package com.spring.service.user;

import com.spring.entity.User;

import java.util.List;

public interface UserServiceInterface {

    User createUser(String login);

    User getUserById(Long id);

    List<User> getAllUsers();
}
