package com.spring;


import com.spring.entity.Account;
import com.spring.entity.User;
import com.spring.exceptions.ServiceException;
import com.spring.repository.AccountDao;
import com.spring.repository.UserDao;
import com.spring.service.account.AccountConfig;
import com.spring.service.account.impl.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceTest {

    @Mock
    private AccountDao accountDao;

    @Mock
    private UserDao userDao;

    @Mock
    private AccountConfig accountConfig;

    private AccountService accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(accountConfig.getDefaultAmount()).thenReturn(BigDecimal.valueOf(100));
        when(accountConfig.getTransferAmount()).thenReturn(BigDecimal.valueOf(1));
        accountService = new AccountService(accountConfig, accountDao, userDao);
    }

    @Test
    void createAccount_ValidUserId_ReturnsNewAccount() {
        Long userId = 1L;
        User user = User.builder()
                .id(userId)
                .build();
        when(userDao.findById(userId)).thenReturn(user);
        when(accountDao.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Account result = accountService.createAccount(userId);

        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(100), result.getMoneyAmount());
        assertEquals(user, result.getUser());
        verify(accountDao).save(any(Account.class));
    }

    @Test
    void createAccount_NullUserId_ThrowsServiceException() {
        assertThrows(ServiceException.class, () -> accountService.createAccount(null));
    }

    @Test
    void deposit_ValidAccountIdAndAmount_UpdatesAccount() {
        Long accountId = 1L;
        BigDecimal initialAmount = BigDecimal.valueOf(100);
        BigDecimal depositAmount = BigDecimal.valueOf(50);
        Account account = Account.builder()
                .id(accountId)
                .moneyAmount(initialAmount)
                .build();
        when(accountDao.findById(accountId)).thenReturn(account);

        accountService.deposit(accountId, depositAmount);

        assertEquals(BigDecimal.valueOf(150), account.getMoneyAmount());
        verify(accountDao).update(account);
    }

    @Test
    void withdraw_InsufficientFunds_ThrowsServiceException() {
        Long accountId = 1L;
        BigDecimal initialAmount = BigDecimal.valueOf(100);
        BigDecimal withdrawAmount = BigDecimal.valueOf(150);
        Account account = Account.builder()
                .id(accountId)
                .moneyAmount(initialAmount)
                .build();

        when(accountDao.findById(accountId)).thenReturn(account);

        assertThrows(ServiceException.class, () -> accountService.withdraw(accountId, withdrawAmount));
    }


}

