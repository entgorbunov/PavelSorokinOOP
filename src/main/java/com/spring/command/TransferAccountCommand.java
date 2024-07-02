package com.spring.command;

import com.spring.entity.Account;
import com.spring.service.account.impl.AccountService;

import java.math.BigDecimal;

public class TransferAccountCommand extends AbstractCommand {
    private final AccountService accountService;
    private final Account sourceAccount;
    private final Account destinationAccount;
    private final BigDecimal amount;



    public TransferAccountCommand(Account sourceAccount, Account destinationAccount, AccountService accountService, BigDecimal amount) {
        super(CommandType.ACCOUNT_TRANSFER);
        this.accountService = accountService;
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
        this.amount = amount;
    }


    @Override
    public void execute() {
        accountService.transfer(sourceAccount.getId(), destinationAccount.getId(), amount);
    }
}
