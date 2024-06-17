package com.oop.spring.service.account.impl;

import com.oop.spring.entity.Account;
import com.oop.spring.entity.User;
import com.oop.spring.service.account.AccountServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.*;

public class AccountService implements AccountServiceInterface {
    private BigDecimal defaultAmount;
    private BigDecimal transferAmount;
    private Map<Long, Account> accounts = new HashMap<>();
    private Long accountIdGenerator;

    public AccountService(BigDecimal defaultAmount, BigDecimal transferAmount) {
        this.defaultAmount = defaultAmount;
        this.transferAmount = transferAmount;
        this.accountIdGenerator = 0L;
    }

    @Override
    public Account createAccount(Long userId) {
        Account account = new Account(accountIdGenerator++, userId, defaultAmount);
        accounts.put(account.getId(), account);
        return account;
    }

    @Override
    public void deposit(Long accountId, BigDecimal amount) {
        Account account = accounts.get(accountId);
        account.setMoneyAmount(account.getMoneyAmount().add(amount));
    }

    @Override
    public void withdraw(Long accountId, BigDecimal amount) {
        Account account = accounts.get(accountId);
        if (account.getMoneyAmount().compareTo(amount) < 0) {
            throw new RuntimeException("No such money to withdraw from account");
        }
        account.setMoneyAmount(account.getMoneyAmount().subtract(amount));
    }

    @Override
    public void transfer(Long sourceAccountId, Long targetAccountId, BigDecimal amount) {
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
        accounts.remove(accountId);
    }
}
