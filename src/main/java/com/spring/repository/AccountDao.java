package com.spring.repository;

import com.spring.entity.Account;
import com.spring.exceptions.DaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class AccountDao implements Dao<Account> {

    private static final Logger logger = LoggerFactory.getLogger(AccountDao.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Account save(Account account) {
        logger.info("Saving account: {}", account);
        try {
            entityManager.persist(account);
            logger.info("Account saved successfully with ID: {}", account.getId());
            return account;
        } catch (DaoException e) {
            logger.error("Error saving account: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public Account update(Account account) {
        logger.info("Updating account: {}", account);
        try {
            Account updatedAccount = entityManager.merge(account);
            logger.info("Account updated successfully: {}", updatedAccount);
            return updatedAccount;
        } catch (DaoException e) {
            logger.error("Error updating account: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public void delete(Account account) {
        logger.info("Deleting account: {}", account);
        try {
            entityManager.remove(account);
            logger.info("Account deleted successfully");
        } catch (DaoException e) {
            logger.error("Error deleting account: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Account findById(Long id) {
        logger.info("Finding account by ID: {}", id);
        try {
            Account account = entityManager.find(Account.class, id);
            if (account != null) {
                logger.info("Account found: {}", account);
            } else {
                logger.warn("Account not found for ID: {}", id);
            }
            return account;
        } catch (DaoException e) {
            logger.error("Error finding account by ID: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Account> findAll() {
        logger.info("Finding all accounts");
        try {
            List<Account> accounts = entityManager.createQuery("FROM Account", Account.class).getResultList();
            logger.info("Found {} accounts", accounts.size());
            return accounts;
        } catch (DaoException e) {
            logger.error("Error finding all accounts: {}", e.getMessage());
            throw e;
        }
    }
}
