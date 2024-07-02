package com.spring.service;

import com.spring.command.Command;
import com.spring.command.CommandFactory;
import com.spring.command.CommandType;
import com.spring.service.account.impl.AccountService;
import com.spring.service.user.impl.UserService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Scanner;

@Component
public class OperationsConsoleListener {

    private static final Logger logger = LoggerFactory.getLogger(OperationsConsoleListener.class);
    private final CommandFactory commandFactory;
    private final Scanner scanner;
    private boolean isRunning = true;

    public OperationsConsoleListener(UserService userService, AccountService accountService) {
        this.scanner = new Scanner(System.in);
        this.commandFactory = new CommandFactory(userService, accountService, scanner);
    }

    @PostConstruct
    public void start() {
        logger.info("Starting Operations Console Listener");
        printWelcomeMessage();

        while (isRunning) {
            logger.info("Waiting for user input");
            logger.info("Please enter one of operation types: {}", Arrays.toString(CommandType.values()));
            logger.info("Or type 'EXIT' to quit the application");

            String commandString = scanner.nextLine().trim().toUpperCase();

            if ("EXIT".equals(commandString)) {
                isRunning = false;
                logger.info("Exiting application");
                continue;
            }

            try {
                CommandType commandType = CommandType.valueOf(commandString);
                Command command = commandFactory.createCommand(commandType);
                logger.info("Executing command: {}", commandType);
                command.execute();
            } catch (IllegalArgumentException e) {
                logger.warn("Unknown command: {}", commandString);
                logger.info("Unknown command: {}", commandString);
            } catch (Exception e) {
                logger.error("Error executing command {}: {}", commandString, e.getMessage(), e);
                logger.info("Error executing command {}: {}", commandString, e.getMessage());
                logger.info("Please try again or contact support if the problem persists.");
            }
        }

        scanner.close();
        logger.info("Thank you for using our application. Goodbye!");
    }

    private void printWelcomeMessage() {
        logger.info("Welcome to the Banking Operations Console!");
        logger.info("Available commands: {}", Arrays.toString(CommandType.values()));
        logger.info("Type a command to execute the corresponding operation.");
        logger.info("Type 'EXIT' to quit the application.");
        logger.info("----------------------------------------");
    }
}
