package com.spring.command;

import com.spring.entity.Account;
import com.spring.entity.User;
import com.spring.service.account.impl.AccountService;

public class CreateAccountCommand extends AbstractCommand {
    private final AccountService accountService;
    private final User user;


    public CreateAccountCommand(AccountService accountService, User user) {
        super(CommandType.ACCOUNT_CREATE);
        this.accountService = accountService;
        this.user = user;
    }


    @Override
    public void execute() {
        accountService.createAccount(user.getId());
    }
}
