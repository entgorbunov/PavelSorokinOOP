package com.spring.command;

import com.spring.entity.Account;
import com.spring.service.account.impl.AccountService;

public class CloseAccountCommand extends AbstractCommand {
    private final AccountService accountService;
    private final Long accountId;

    public CloseAccountCommand(AccountService accountService, Long accountId) {
        super(CommandType.ACCOUNT_CLOSE);
        this.accountService = accountService;

        this.accountId = accountId;
    }


    @Override
    public void execute() {
        accountService.closeAccount(accountId);
    }
}
