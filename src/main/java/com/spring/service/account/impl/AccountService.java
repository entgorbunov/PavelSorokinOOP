package com.spring.service.account.impl;

import com.spring.entity.Account;
import com.spring.entity.User;
import com.spring.service.account.AccountServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.*;

public class AccountService implements AccountServiceInterface {
    private final BigDecimal defaultAmount;
    private final BigDecimal transferAmount;
    private final Map<Long, Account> accounts = new HashMap<>();
    private Long accountIdGenerator;

    public AccountService(BigDecimal defaultAmount, BigDecimal transferAmount) {
        this.defaultAmount = defaultAmount;
        this.transferAmount = transferAmount;
        this.accountIdGenerator = 0L;
    }

    @Override
    public Account createAccount(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        Account account = new Account(accountIdGenerator++, userId, defaultAmount);
        accounts.put(account.getId(), account);
        return account;
    }


    @Override
    public void deposit(Long accountId, BigDecimal amount) {
        if (accountId == null) {
            throw new IllegalArgumentException("Account ID cannot be null");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be a positive value");
        }
        Account account = accounts.get(accountId);

        account.setMoneyAmount(account.getMoneyAmount().add(amount));
    }


    @Override
    public void withdraw(Long accountId, BigDecimal amount) {

        if (accountId == null) {
            throw new IllegalArgumentException("Account ID cannot be null");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be a positive value");
        }

        Account account = accounts.get(accountId);
        if (account.getMoneyAmount().compareTo(amount) < 0) {
            throw new RuntimeException("No such money to withdraw from the account");
        }
        account.setMoneyAmount(account.getMoneyAmount().subtract(amount));
    }

    @Override
    public void transfer(Long sourceAccountId, Long targetAccountId, BigDecimal amount) {
        if (sourceAccountId == null) {
            throw new IllegalArgumentException("Source account ID cannot be null");
        }
        if (targetAccountId == null) {
            throw new IllegalArgumentException("Target account ID cannot be null");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be a positive value");
        }
        Account sourceAccount = accounts.get(sourceAccountId);
        Account targetAccount = accounts.get(targetAccountId);
        BigDecimal commission = BigDecimal.ZERO;
        if (!sourceAccount.getUserId().equals(targetAccount.getUserId())) {
            commission = amount.multiply(transferAmount).divide(BigDecimal.valueOf(100), 2);
        }
        withdraw(sourceAccountId, amount.add(commission));
        deposit(targetAccountId, amount);
    }

    @Override
    public void closeAccount(Long accountId) {
        if (accountId == null) {
            throw new IllegalArgumentException("Account ID cannot be null");
        }
        accounts.remove(accountId);
    }
}
