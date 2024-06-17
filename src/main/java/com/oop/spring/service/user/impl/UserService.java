package com.oop.spring.service.user.impl;

import com.oop.spring.entity.Account;
import com.oop.spring.service.account.impl.AccountService;
import com.oop.spring.service.user.UserServiceInterface;
import com.oop.spring.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements UserServiceInterface {
    private AccountService accountService;

    private HashMap<Long, User> users = new HashMap<>();
    private Long userIdGenerator;

    @Autowired
    public UserService(AccountService accountService) {
        this.accountService = accountService;
        this.userIdGenerator = 0L;
    }

    @Override
    public User createUser(String login) {
        User user = new User(userIdGenerator++, login, new ArrayList<>());
        Account account = accountService.createAccount(user.getId());
        user.getAccountList().add(account);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User getUserById(Long id) {
        return users.get(id);
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

}
