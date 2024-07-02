package com.spring.command;

import com.spring.entity.Account;
import com.spring.service.account.impl.AccountService;

import java.math.BigDecimal;

public class DepositAccountCommand extends AbstractCommand {
    private final AccountService accountService;
    private final Account account;
    private final BigDecimal amount;

    public DepositAccountCommand(Account account, AccountService accountService, BigDecimal amount) {
        super(CommandType.ACCOUNT_DEPOSIT);
        this.accountService = accountService;
        this.account = account;
        this.amount = amount;
    }

    @Override
    public void execute() {
        accountService.deposit(account.getId(), amount);
    }
}
