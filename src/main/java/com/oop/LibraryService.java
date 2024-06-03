package com.oop;

import java.util.*;

public class LibraryService {
    private final List<Book> books;
    private final List<User> users;
    private final HashMap<Long, Set<Long>> userBooks;

    public LibraryService(List<Book> books, List<User> users) {
        this.books = books;
        this.users = users;
        this.userBooks = new HashMap<>();
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }

    public List<Book> getAllAvailableBooks() {
        Set<Long> checkedOutBookIds = new HashSet<>();
        for (Set<Long> bookIds : userBooks.values()) {
            checkedOutBookIds.addAll(bookIds);
        }

        List<Book> availableBooks = new ArrayList<>();
        for (Book book : books) {
            if (!checkedOutBookIds.contains(book.getBookId())) {
                availableBooks.add(book);
            }
        }
        return availableBooks;
    }

    public List<Book> getUserBooks(long userId) {
        validateUserId(userId);
        Set<Long> bookIds = userBooks.getOrDefault(userId, new HashSet<>());
        List<Book> booksByUser = new ArrayList<>();
        for (Book book : books) {
            if (bookIds.contains(book.getBookId())) {
                booksByUser.add(book);
            }
        }
        return booksByUser;
    }

    public boolean takeBook(long userId, long bookId) {
        validateBookId(bookId);
        validateUserId(userId);
        for (Set<Long> bookIds : userBooks.values()) {
            if (bookIds.contains(bookId)) {
                return false;
            }
        }
        Set<Long> booksByUser = userBooks.computeIfAbsent(userId, k -> new HashSet<>());
        booksByUser.add(bookId);
        return true;
    }

    public boolean returnBook(long userId, long bookId) {
        validateBookId(bookId);
        validateUserId(userId);
        Set<Long> booksByUser = userBooks.get(userId);
        if (booksByUser != null && booksByUser.contains(bookId)) {
            booksByUser.remove(Long.valueOf(bookId));
            if (booksByUser.isEmpty()) {
                userBooks.remove(userId);
            }
            return true;
        }
        return false;
    }

    private void validateUserId(long userId) {
        boolean userExists = users.stream().anyMatch(user -> user.getUserId() == userId);
        if (!userExists) {

            throw new IllegalArgumentException("Unknown user ID: " + userId);
        }
    }

    private void validateBookId(long bookId) {
        boolean bookExists = books.stream().anyMatch(book -> book.getBookId() == bookId);
        if (!bookExists) {
            throw new IllegalArgumentException("Unknown book ID: " + bookId);
        }
    }
}
