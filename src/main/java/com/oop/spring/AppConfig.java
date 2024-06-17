package com.oop.spring;

import com.oop.spring.service.OperationsConsoleListener;
import com.oop.spring.service.account.impl.AccountService;
import com.oop.spring.service.user.impl.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.math.BigDecimal;

@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Value("${account.default-amount}")
    private BigDecimal defaultAmount;

    @Value("${account.transfer-amount}")
    private BigDecimal transferAmount;

    @Bean
    public UserService userService(AccountService accountService) {
        return new UserService(accountService);
    }

    @Bean
    public AccountService accountService() {
        return new AccountService(defaultAmount, transferAmount);
    }

    @Bean
    public OperationsConsoleListener operationConsoleListener(UserService userService, AccountService accountService) {
        return new OperationsConsoleListener(userService, accountService);
    }


}
