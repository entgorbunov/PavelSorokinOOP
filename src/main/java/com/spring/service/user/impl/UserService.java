package com.spring.service.user.impl;

import com.spring.entity.Account;
import com.spring.service.account.impl.AccountService;
import com.spring.service.user.UserServiceInterface;
import com.spring.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

public class UserService implements UserServiceInterface {
    private final AccountService accountService;

    private final HashMap<Long, User> users = new HashMap<>();
    private Long userIdGenerator;

    @Autowired
    public UserService(AccountService accountService) {
        this.accountService = accountService;
        this.userIdGenerator = 0L;
    }

    @Override
    public User createUser(String login) {
        if (login == null) {
            throw new IllegalArgumentException("User login cannot be null");
        }

        for (User user : users.values()) {
            if (user.getLogin().equals(login)) {
                throw new IllegalArgumentException("User with login " + login + " already exists");
            }
        }
        User user = new User(userIdGenerator++, login, new ArrayList<>());
        Account account = accountService.createAccount(user.getId());
        user.getAccountList().add(account);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User getUserById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        return users.get(id);
    }

    @Override
    public List<User> getAllUsers() {

        return new ArrayList<>(users.values());
    }

}
