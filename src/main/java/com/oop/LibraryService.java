package com.oop;

import java.util.*;

public class LibraryService {
    private final List<Book> books;
    private final List<User> users;
    private final HashMap<Long, List<Long>> userBooks;

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
        for (List<Long> bookIds : userBooks.values()) {
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
        List<Long> bookIds = userBooks.getOrDefault(userId, new ArrayList<>());
        List<Book> booksByUser = new ArrayList<>();
        for (Book book : books) {
            if (bookIds.contains(book.getBookId())) {
                booksByUser.add(book);
            }
        }
        return booksByUser;
    }

    public boolean takeBook(long userId, long bookId) {
        for (List<Long> bookIds : userBooks.values()) {
            if (bookIds.contains(bookId)) {
                return false;
            }
        }
        List<Long> booksByUser = userBooks.computeIfAbsent(userId, k -> new ArrayList<>());
        booksByUser.add(bookId);
        return true;
    }

    public boolean returnBook(long userId, long bookId) {
        List<Long> booksByUser = userBooks.get(userId);
        if (booksByUser != null && booksByUser.contains(bookId)) {
            booksByUser.remove(Long.valueOf(bookId));
            if (booksByUser.isEmpty()) {
                userBooks.remove(userId);
            }
            return true;
        }
        return false;
    }
}
