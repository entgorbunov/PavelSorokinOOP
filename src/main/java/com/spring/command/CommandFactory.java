package com.spring.command;

import com.spring.entity.Account;
import com.spring.service.account.impl.AccountService;
import com.spring.service.user.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Scanner;

@Component
public class CommandFactory {

    private final UserService userService;
    private final AccountService accountService;
    private final Scanner scanner;

    @Autowired
    public CommandFactory(UserService userService, AccountService accountService, Scanner scanner) {
        this.userService = userService;
        this.accountService = accountService;
        this.scanner = scanner;
    }

    public Command createCommand(CommandType type) {
        return switch (type) {
            case USER_CREATE -> createUserCommand();
            case SHOW_ALL_USERS -> new ShowAllUsersCommand(userService);
            case ACCOUNT_CREATE -> createAccountCommand();
            case ACCOUNT_CLOSE -> closeAccountCommand();
            case ACCOUNT_DEPOSIT -> depositAccountCommand();
            case ACCOUNT_TRANSFER -> transferAccountCommand();
            case ACCOUNT_WITHDRAW -> withdrawAccountCommand();
        };
    }

    private Command createUserCommand() {
        System.out.println("Enter login for new user:");
        String login = scanner.nextLine();
        return new CreateUserCommand(userService, login);
    }

    private Command createAccountCommand() {
        System.out.println("Enter user ID for new account:");
        Long userId = Long.parseLong(scanner.nextLine());
        return new CreateAccountCommand(accountService, userService.getUserById(userId));
    }

    private Command closeAccountCommand() {
        System.out.println("Enter account ID for closing:");
        Long accountId = Long.parseLong(scanner.nextLine());
        return new CloseAccountCommand(accountService, accountId);
    }

    private Command depositAccountCommand() {
        System.out.println("Enter account ID for deposit:");
        Long accountId = Long.parseLong(scanner.nextLine());
        System.out.println("Enter amount for deposit:");
        BigDecimal amount = new BigDecimal(scanner.nextLine());
        return new DepositAccountCommand(accountService.getAccountById(accountId), accountService, amount);
    }

    private Command transferAccountCommand() {
        System.out.println("Enter source account ID for transfer:");
        Long fromAccountId = Long.parseLong(scanner.nextLine());
        System.out.println("Enter destination account ID for transfer:");
        Long toAccountId = Long.parseLong(scanner.nextLine());
        System.out.println("Enter amount for transfer:");
        BigDecimal amount = new BigDecimal(scanner.nextLine());
        Account fromAccount = accountService.getAccountById(fromAccountId);
        Account toAccount = accountService.getAccountById(toAccountId);
        return new TransferAccountCommand(fromAccount, toAccount, accountService, amount);
    }

    private Command withdrawAccountCommand() {
        System.out.println("Enter account ID for withdraw:");
        Long accountId = Long.parseLong(scanner.nextLine());

        System.out.println("Enter amount for withdraw:");
        BigDecimal amount = new BigDecimal(scanner.nextLine());
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Amount must be positive. Please try again.");
        }
        return new WithdrawAccountCommand(accountId, accountService, amount);
    }
}
