package fr.imt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.List;


import static org.mockito.Mockito.mock;

public class LibraryTest {

    private Library library;
    private Subscriber jhon;

    @BeforeEach
    public void init() {
        library = mock(Library.class);
        jhon = mock(Subscriber.class);
    }

    @Test
    @DisplayName("S1 : Login Fail with exception")
    public void logInFailWithException() {
        library = mock(Library.class);

        assertThrows(IllegalArgumentException.class,
                () -> library.logIn("sdfghj", "dfghjk"),
                "Invalid login should throw IllegalArgumentException");
    }

    @Test
    @DisplayName("S2 : Searching a book by type is successfull")
    public void searchBookSuccess() {

        Book book1 = mock(Book.class);
        Book book2 = mock(Book.class);
        Book book3 = mock(Book.class);
        Book book4 = mock(Book.class);

        when(book1.getType()).thenReturn("Roman");
        when(book2.getType()).thenReturn("Polars");
        when(book3.getType()).thenReturn("Polars");
        when(book4.getType()).thenReturn("Essai");

        when(jhon.getId()).thenReturn(0);
        when(jhon.getUsername()).thenReturn("John");
        when(jhon.getPassword()).thenReturn("password");

        library.addSubscriber(jhon);
        library.logIn("John", "password");

        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);
        library.addBook(book4);

        HashMap<String, String> params = new HashMap<>();
        params.put("category", "Polars");

        List<Book> polarsBooks = library.searchBook(params);
        assertEquals(2, polarsBooks.size());
        assertTrue(polarsBooks.contains(book2));
        assertTrue(polarsBooks.contains(book3));
    }

    @Test
    @DisplayName("S3: Searching for a missing category")
    public void searchForMissingCategory() {
        HashMap<String,String> search = new HashMap<>();
        search.put("category", "Voyage");

        assertEquals(0, library.searchBook(search).size(),
            "Searching for a missing category should return an empty array");
    }


}
