package com.spring.command;

import com.spring.entity.Account;
import com.spring.service.account.impl.AccountService;

import java.math.BigDecimal;

public class WithdrawAccountCommand extends AbstractCommand {

    private final AccountService accountService;
    private final Long accountId;
    private final BigDecimal amount;

    public WithdrawAccountCommand(Long accountId, AccountService accountService, BigDecimal amount) {
        super(CommandType.ACCOUNT_WITHDRAW);
        this.accountService = accountService;
        this.accountId = accountId;
        this.amount = amount;
    }

    @Override
    public void execute() {
        accountService.withdraw(accountId, amount);
    }

}
