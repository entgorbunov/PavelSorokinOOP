package com.spring.service.account;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.math.BigDecimal;
@Getter
@Configuration
@PropertySource("classpath:application.properties")
public class AccountConfig {
    @Value("${account.default-amount}")
    private BigDecimal defaultAmount;

    @Value("${account.transfer-amount}")
    private BigDecimal transferAmount;


}
