package com.oop.spring.service.account;

import com.oop.spring.entity.Account;

import java.math.BigDecimal;

public interface AccountServiceInterface {
    Account createAccount(Long userId);
    void deposit(Long accountId, BigDecimal amount);
    void withdraw(Long accountId, BigDecimal amount);
    void transfer(Long sourceAccountId, Long targetAccountId, BigDecimal amount);
    void closeAccount(Long accountId);
}
