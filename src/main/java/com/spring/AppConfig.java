package com.spring;

import com.spring.service.OperationsConsoleListener;
import com.spring.service.account.impl.AccountService;
import com.spring.service.user.impl.UserService;
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
