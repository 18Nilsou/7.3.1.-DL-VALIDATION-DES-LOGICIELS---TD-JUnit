package fr.imt;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LibraryTest {

    @Test
    public void logInFailWithException() {
        Library library = mock(Library.class);
        Subscriber user = mock(Subscriber.class);
        assertThrows(IllegalArgumentException.class,
            () -> library.logIn(user),
            "Invalid login should throw IllegalArgumentException");
    } 

    @Test
    public void searchTypeBookSuccess() {
        
        Library library = mock(Library.class);
        Book book1 = mock(Book.class);
        Book book2 = mock(Book.class);
        Book book3 = mock(Book.class);
        Book book4 = mock(Book.class);
        when(book1.getType()).thenReturn("Roman");
        when(book2.getType()).thenReturn("Polars");
        when(book3.getType()).thenReturn("Polars");
        when(book4.getType()).thenReturn("Essai");

        Subscriber user = mock(Subscriber.class);
        when(user.getId()).thenReturn(0);
        when(user.getUsername()).thenReturn("John");
        when(user.getPassword()).thenReturn("password");
        
        library.addSubscriber(user);
        library.logIn(0, "John", "password");
        
        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);
        library.addBook(book4);

        Map<String, String> params = new HashMap<>();
        params.put("category", "Polars");

        List<Book> polarsBooks = library.searchTypeBook(params);
        assertEquals(2, polarsBooks.size());
        assertTrue(polarsBooks.contains(book2));
        assertTrue(polarsBooks.contains(book3));
    }    
}
