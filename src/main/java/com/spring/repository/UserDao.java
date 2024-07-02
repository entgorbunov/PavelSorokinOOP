package com.spring.repository;

import com.spring.entity.User;
import com.spring.exceptions.DaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDao implements Dao<User> {

    private static final Logger logger = LoggerFactory.getLogger(UserDao.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User save(User user) {
        logger.info("Saving user: {}", user);
        try {
            entityManager.persist(user);
            logger.info("User saved successfully with ID: {}", user.getId());
            return user;
        } catch (DaoException e) {
            logger.error("Error saving user: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public User update(User user) {
        logger.info("Updating user: {}", user);
        try {
            User updatedUser = entityManager.merge(user);
            logger.info("User updated successfully: {}", updatedUser);
            return updatedUser;
        } catch (DaoException e) {
            logger.error("Error updating user: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public void delete(User user) {
        logger.info("Deleting user: {}", user);
        try {
            entityManager.remove(user);
            logger.info("User deleted successfully");
        } catch (DaoException e) {
            logger.error("Error deleting user: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public User findById(Long id) {
        logger.info("Finding user by ID: {}", id);
        try {
            User user = entityManager.find(User.class, id);
            if (user != null) {
                logger.info("User found: {}", user);
            } else {
                logger.warn("User not found for ID: {}", id);
            }
            return user;
        } catch (DaoException e) {
            logger.error("Error finding user by ID: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public List<User> findAll() {
        logger.info("Finding all users");
        try {
            List<User> users = entityManager.createQuery("FROM User", User.class).getResultList();
            logger.info("Found {} users", users.size());
            return users;
        } catch (DaoException e) {
            logger.error("Error finding all users: {}", e.getMessage());
            throw e;
        }
    }

    public Optional<User> findByLogin(String login) {
        logger.info("Finding user by login: {}", login);
        try {
            User user = entityManager.createQuery("SELECT u FROM User u WHERE u.login = :login", User.class)
                    .setParameter("login", login)
                    .getSingleResult();
            logger.info("User found: {}", user);
            return Optional.of(user);
        } catch (NoResultException e) {
            logger.warn("No user found for login: {}", login);
            return Optional.empty();
        } catch (DaoException e) {
            logger.error("Error finding user by login: {}", e.getMessage());
            throw e;
        }
    }
}
