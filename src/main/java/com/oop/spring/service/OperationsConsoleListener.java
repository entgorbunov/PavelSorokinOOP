package com.oop.spring.service;

import com.oop.spring.entity.Account;
import com.oop.spring.entity.User;
import com.oop.spring.service.account.impl.AccountService;
import com.oop.spring.service.user.impl.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

@Component
public class OperationsConsoleListener {
    private UserService userService;
    private AccountService accountService;
    private Scanner scanner;

    public OperationsConsoleListener(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
        this.scanner = new Scanner(System.in);
    }

    @PostConstruct
    public void start() {
        while (true) {
            System.out.println("Please enter one of operation type: ");
            String command = scanner.nextLine();

            try {
                switch (command) {
                    case "USER_CREATE":
                        createUser();
                        break;
                    case "SHOW_ALL_USERS":
                        showAllUsers();
                        break;
                    case "ACCOUNT_CREATE":
                        createAccount();
                        break;
                    case "ACCOUNT_CLOSE":
                        closeAccount();
                        break;
                    case "ACCOUNT_DEPOSIT":
                        deposit();
                        break;
                    case "ACCOUNT_TRANSFER":
                        transfer();
                        break;
                    case "ACCOUNT_WITHDRAW":
                        withdraw();
                        break;
                    default:
                        System.out.println("Unknown command: " + command);
                }

            } catch (Exception e) {
                System.out.println("Error executing command " + command + ": error=" + e.getMessage());
            }
            }
    }

    private void createUser() {
        System.out.println("Enter login for new user:");
        String login = scanner.nextLine();
        User user = userService.createUser(login);
        System.out.println("User created: " + user);
    }

    private void showAllUsers() {
        System.out.println("List of users:");
        List<User> users = userService.getAllUsers();
        users.forEach(System.out::println);
    }

    private void createAccount() {
        System.out.println("Enter login for new account:");
        Long userId = Long.parseLong(scanner.nextLine());
        Account account = accountService.createAccount(userId);
        System.out.println("New account created with ID: " + account.getId() + " for user: " + userService.getUserById(userId).getLogin());

    }

    private void deposit() {
        System.out.println("Enter account ID:");
        Long accountId = Long.parseLong(scanner.nextLine());
        System.out.println("Enter amount to deposit:");
        BigDecimal amount = new BigDecimal(scanner.nextLine());
        accountService.deposit(accountId, amount);
        System.out.println("Amount " + amount + " deposited to account ID: " + accountId);
    }

    private void transfer() {
        System.out.println("Enter source account ID:");
        Long sourceAccountId = Long.parseLong(scanner.nextLine());
        System.out.println("Enter target account ID:");
        Long targetAccountId = Long.parseLong(scanner.nextLine());
        System.out.println("Enter amount to transfer:");
        BigDecimal amount = new BigDecimal(scanner.nextLine());
        accountService.transfer(sourceAccountId, targetAccountId, amount);
        System.out.println("Amount " + amount + " transferred from account ID " + sourceAccountId + " to account ID " + targetAccountId + ".");
    }

    private void withdraw() {
        System.out.println("Enter account ID to withdraw from:");
        Long accountId = Long.parseLong(scanner.nextLine());
        System.out.println("Enter amount to withdraw:");
        BigDecimal amount = new BigDecimal(scanner.nextLine());
        accountService.withdraw(accountId, amount);
        System.out.println("Amount " + amount + " withdrawn from account ID: " + accountId);
    }

    private void closeAccount() {
        System.out.println("Enter account ID to close:");
        Long accountId = Long.parseLong(scanner.nextLine());
        accountService.closeAccount(accountId);
        System.out.println("Account with ID " + accountId + " has been closed.");
    }
}
