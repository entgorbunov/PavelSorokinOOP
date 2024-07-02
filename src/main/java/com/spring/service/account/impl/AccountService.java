package com.spring.service.account.impl;

import com.spring.entity.Account;
import com.spring.entity.User;
import com.spring.exceptions.ServiceException;
import com.spring.repository.AccountDao;
import com.spring.repository.UserDao;
import com.spring.service.account.AccountConfig;
import com.spring.service.account.AccountServiceInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@Transactional
public class AccountService implements AccountServiceInterface {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    private final BigDecimal defaultAmount;
    private final AccountDao accountDao;
    private final UserDao userDao;
    private final AccountConfig accountConfig;

    public AccountService(
            AccountConfig accountConfig,
            AccountDao accountDao,
            UserDao userDao) {
        this.defaultAmount = accountConfig.getDefaultAmount();
        this.accountDao = accountDao;
        this.userDao = userDao;
        this.accountConfig = accountConfig;
        logger.info("AccountService initialized with default amount: {}", defaultAmount);
    }

    @Override
    @Transactional
    public Account createAccount(Long userId) {
        logger.info("Creating account for user with ID: {}", userId);
        if (userId == null) {
            logger.error("Attempt to create account with null user ID");
            throw new ServiceException("User ID cannot be null");
        }
        User user = userDao.findById(userId);
        if (user == null) {
            logger.error("User with ID {} not found", userId);
            throw new ServiceException("User with ID " + userId + " has not been found");
        }
        Account account = Account.builder()
                .moneyAmount(defaultAmount)
                .user(user)
                .build();
        Account savedAccount = accountDao.save(account);
        logger.info("Account created successfully for user ID: {}, account ID: {}", userId, savedAccount.getId());
        return savedAccount;
    }

    @Override
    @Transactional
    public void deposit(Long accountId, BigDecimal amount) {
        logger.info("Depositing {} to account ID: {}", amount, accountId);
        if (accountId == null) {
            logger.error("Attempt to deposit to null account ID");
            throw new ServiceException("Account ID cannot be null");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            logger.error("Invalid deposit amount: {}", amount);
            throw new ServiceException("Amount must be a positive value");
        }
        Account account = accountDao.findById(accountId);
        if (account == null) {
            logger.error("Account with ID {} not found", accountId);
            throw new ServiceException("Account with ID " + accountId + " has not been found");
        }
        account.setMoneyAmount(account.getMoneyAmount().add(amount));
        accountDao.update(account);
        logger.info("Deposit successful. New balance for account ID {}: {}", accountId, account.getMoneyAmount());
    }

    public Account getAccountById(Long accountId) {
        logger.info("Retrieving account with ID: {}", accountId);
        if (accountId == null) {
            logger.error("Attempt to get account with null ID");
            throw new ServiceException("Account ID cannot be null");
        }
        Account account = accountDao.findById(accountId);
        if (account == null) {
            logger.warn("Account with ID {} not found", accountId);
        } else {
            logger.info("Account retrieved successfully: {}", account);
        }
        return account;
    }

    @Override
    @Transactional
    public void withdraw(Long accountId, BigDecimal amount) {
        logger.info("Withdrawing {} from account ID: {}", amount, accountId);
        if (accountId == null) {
            logger.error("Attempt to withdraw from null account ID");
            throw new ServiceException("Account ID cannot be null");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            logger.error("Invalid withdrawal amount: {}", amount);
            throw new ServiceException("Amount must be a positive value");
        }
        Account account = getAccountById(accountId);

        if (account.getMoneyAmount().compareTo(amount) < 0) {
            logger.error("Insufficient funds for withdrawal. Account ID: {}, Current balance: {}, Withdrawal amount: {}",
                    accountId, account.getMoneyAmount(), amount);
            throw new ServiceException("Insufficient funds");
        }
        account.setMoneyAmount(account.getMoneyAmount().subtract(amount));
        accountDao.update(account);
        logger.info("Withdrawal successful. New balance for account ID {}: {}", accountId, account.getMoneyAmount());
    }

    @Override
    @Transactional
    public void transfer(Long sourceAccountId, Long targetAccountId, BigDecimal amount) {
        logger.info("Transferring {} from account ID: {} to account ID: {}", amount, sourceAccountId, targetAccountId);
        Account sourceAccount = getAccountById(sourceAccountId);
        Account targetAccount = getAccountById(targetAccountId);

        BigDecimal commission = BigDecimal.ZERO;

        if (!sourceAccount.getUser().equals(targetAccount.getUser())) {
            commission = amount.multiply(accountConfig.getTransferAmount())
                    .setScale(2, RoundingMode.HALF_UP);
            logger.info("Commission applied for inter-user transfer: {}", commission);
        }

        BigDecimal totalDeduction = amount.add(commission);

        if (sourceAccount.getMoneyAmount().compareTo(totalDeduction) < 0) {
            logger.error("Insufficient funds for transfer. Source account ID: {}, Current balance: {}, Transfer amount: {}, Commission: {}",
                    sourceAccountId, sourceAccount.getMoneyAmount(), amount, commission);
            throw new ServiceException("Insufficient funds");
        }

        sourceAccount.setMoneyAmount(sourceAccount.getMoneyAmount().subtract(totalDeduction).setScale(2, RoundingMode.HALF_UP));
        targetAccount.setMoneyAmount(targetAccount.getMoneyAmount().add(amount).setScale(2, RoundingMode.HALF_UP));

        accountDao.update(sourceAccount);
        accountDao.update(targetAccount);
        logger.info("Transfer successful. New balance for source account ID {}: {}, target account ID {}: {}",
                sourceAccountId, sourceAccount.getMoneyAmount(), targetAccountId, targetAccount.getMoneyAmount());
    }

    @Override
    @Transactional
    public void closeAccount(Long accountId) {
        logger.info("Closing account with ID: {}", accountId);
        if (accountId == null) {
            logger.error("Attempt to close account with null ID");
            throw new ServiceException("Account ID cannot be null");
        }
        Account account = accountDao.findById(accountId);
        if (account == null) {
            logger.warn("Attempt to close non-existent account with ID: {}", accountId);
            throw new ServiceException("Account with ID " + accountId + " not found");
        }
        accountDao.delete(account);
        logger.info("Account with ID {} closed successfully", accountId);
    }
}
