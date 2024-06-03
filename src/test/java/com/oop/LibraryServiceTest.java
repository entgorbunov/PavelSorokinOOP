package com.oop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

class LibraryServiceTest {
    private LibraryService service;
    private Book book1, book2, book3;
    private User user1, user2;

    @BeforeEach
    void setUp() {
        book1 = new Book("Book1", "Author1", 2001, 1L);
        book2 = new Book("Book2", "Author2", 2002, 2L);
        book3 = new Book("Book3", "Author3", 2003, 3L);
        user1 = new User("User1", 25, 1L);
        user2 = new User("User2", 30, 2L);
        service = new LibraryService(Arrays.asList(book1, book2, book3), Arrays.asList(user1, user2));
    }

    @Test
    void testTakeBook() {
        assertTrue(service.takeBook(1L, 1L));
        assertFalse(service.takeBook(2L, 1L));
    }

    @Test
    void testReturnBook() {
        service.takeBook(1L, 1L);
        assertTrue(service.returnBook(1L, 1L));
        assertFalse(service.returnBook(1L, 1L));
    }

    @Test
    void testGetAllBooks() {
        List<Book> allBooks = service.getAllBooks();
        assertEquals(3, allBooks.size());
    }

    @Test
    void testGetAllAvailableBooks() {
        service.takeBook(1L, 1L);
        List<Book> availableBooks = service.getAllAvailableBooks();
        assertEquals(2, availableBooks.size());
        assertFalse(availableBooks.contains(book1));
        assertTrue(availableBooks.contains(book2));
        assertTrue(availableBooks.contains(book3));
    }

    @Test
    void testGetUserBooks() {
        service.takeBook(1L, 1L);
        service.takeBook(1L, 2L);
        List<Book> userBooks = service.getUserBooks(1L);
        assertEquals(2, userBooks.size());
        assertTrue(userBooks.contains(book1));
        assertTrue(userBooks.contains(book2));
    }

    @Test
    void testMultipleUsersHandlingBooks() {
        service.takeBook(1L, 1L);
        service.takeBook(2L, 2L);
        assertTrue(service.returnBook(1L, 1L));
        assertFalse(service.returnBook(1L, 2L));
        List<Book> user1Books = service.getUserBooks(1L);
        List<Book> user2Books = service.getUserBooks(2L);
        assertTrue(user1Books.isEmpty());
        assertEquals(1, user2Books.size());
        assertTrue(user2Books.contains(book2));
    }
}
