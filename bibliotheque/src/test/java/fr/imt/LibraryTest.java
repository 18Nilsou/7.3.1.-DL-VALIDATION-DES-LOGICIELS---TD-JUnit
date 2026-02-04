package fr.imt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

public class LibraryTest {

    private ILibrary library;
    private ISubscriber john;
    private ISubscriber johnny;
    private IBook book1;
    private IBook book2;
    private IBook book3;

    @BeforeEach
    public void init() {
        library = mock(ILibrary.class);
        
        john = mock(ISubscriber.class);
        when(john.getId()).thenReturn(1);
        when(john.getUsername()).thenReturn("John");
        when(john.getPassword()).thenReturn("password1");
        
        johnny = mock(ISubscriber.class);
        when(johnny.getId()).thenReturn(2);
        when(johnny.getUsername()).thenReturn("Johnny");
        when(johnny.getPassword()).thenReturn("password2");
        
        book1 = mock(IBook.class);
        book2 = mock(IBook.class);
        book3 = mock(IBook.class);
    }

    @Test
    @DisplayName("S1: john cherche à se connecter avec des identifiants invalides")
    public void testS1_LoginFailWithException() {
        when(library.logIn("john", "wrongpassword")).thenThrow(new IllegalArgumentException("Invalid credentials"));
        
        assertThrows(IllegalArgumentException.class,
                () -> library.logIn("john", "wrongpassword"),
                "Invalid login should throw IllegalArgumentException");
    }

    @Test
    @DisplayName("S2: johnny se connecte et recherche des livres Polar")
    public void testS2_LoginSuccessAndSearchPolars() {
        when(book1.getType()).thenReturn("Polar");
        when(book2.getType()).thenReturn("Polar");
        when(book3.getType()).thenReturn("Roman");
        
        when(library.logIn("johnny", "password2")).thenReturn(true);
        
        HashMap<String, String> searchParams = new HashMap<>();
        searchParams.put("category", "Polar");
        
        List<IBook> polarBooks = Arrays.asList(book1, book2);
        when(library.searchBook(searchParams)).thenReturn(polarBooks);
        
        assertTrue(library.logIn("johnny", "password2"));
        
        List<IBook> result = library.searchBook(searchParams);
        assertEquals(2, result.size());
        assertTrue(result.contains(book1));
        assertTrue(result.contains(book2));
    }

    @Test
    @DisplayName("S3: Recherche d'une catégorie inexistante (Voyage)")
    public void testS3_SearchForMissingCategory() {
        HashMap<String, String> searchParams = new HashMap<>();
        searchParams.put("category", "Voyage");
        
        when(library.searchBook(searchParams)).thenReturn(Collections.emptyList());
        
        List<IBook> result = library.searchBook(searchParams);
        assertEquals(0, result.size(), 
            "Searching for a missing category should return an empty list");
    }

    @Test
    @DisplayName("S4: Réservation d'un ouvrage existant mais indisponible")
    public void testS4_BookUnavailableBook() {
        Date reservationDate = new Date();
        
        when(library.addBooking(book1, johnny, reservationDate)).thenReturn(true);
        when(book1.addInLine(johnny)).thenReturn(true);
        
        boolean result = library.addBooking(book1, johnny, reservationDate);
        
        assertTrue(result, "Booking should be added successfully");
        verify(library).addBooking(book1, johnny, reservationDate);
    }

    @Test
    @DisplayName("S5: Réservation d'un ouvrage existant et disponible")
    public void testS5_BookAvailableBook() {
        Date reservationDate = new Date();
        
        when(library.addBooking(book1, johnny, reservationDate))
            .thenThrow(new IllegalStateException("Book is available, please borrow it instead"));
        
        assertThrows(IllegalStateException.class,
            () -> library.addBooking(book1, johnny, reservationDate),
            "Should suggest to borrow the book instead of booking");
    }

    @Test
    @DisplayName("S6: Réservation d'un ouvrage n'existant pas dans le fonds")
    public void testS6_BookNonExistentBook() {
        IBook nonExistentBook = mock(IBook.class);
        Date reservationDate = new Date();
        
        when(library.addBooking(nonExistentBook, johnny, reservationDate))
            .thenThrow(new IllegalArgumentException("Book does not exist in catalog"));
        
        assertThrows(IllegalArgumentException.class,
            () -> library.addBooking(nonExistentBook, johnny, reservationDate),
            "Booking a non-existent book should throw IllegalArgumentException");
    }

    @Test
    @DisplayName("S7: Un abonné s'identifie et obtient la liste de ses emprunts en retard")
    public void testS7_GetOverdueBorrowings() {
        when(library.logIn("johnny", "password2")).thenReturn(true);
        
        HashMap<String, String> searchOverdue = new HashMap<>();
        searchOverdue.put("subscriber", johnny.getUsername());
        searchOverdue.put("status", "overdue");
        
        List<IBook> overdueBooks = Arrays.asList(book1, book2);
        when(library.searchBook(searchOverdue)).thenReturn(overdueBooks);
        
        assertTrue(library.logIn("johnny", "password2"));
        List<IBook> result = library.searchBook(searchOverdue);
        
        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("S8: Livre emprunté le 30 janvier, consultation le 1er mars - livre en retard")
    public void testS8_BookOverdueAfterOneMonth() {
        Calendar borrowDate = Calendar.getInstance();
        borrowDate.set(2024, Calendar.JANUARY, 30);
        
        Calendar checkDate = Calendar.getInstance();
        checkDate.set(2024, Calendar.MARCH, 1);
        
        Calendar expectedReturnDate = Calendar.getInstance();
        expectedReturnDate.set(2024, Calendar.MARCH, 1);
        
        when(library.logIn("johnny", "password2")).thenReturn(true);
        
        HashMap<String, String> searchOverdue = new HashMap<>();
        searchOverdue.put("subscriber", johnny.getUsername());
        searchOverdue.put("status", "overdue");
        searchOverdue.put("checkDate", checkDate.getTime().toString());
        
        List<IBook> overdueBooks = Arrays.asList(book1);
        when(library.searchBook(searchOverdue)).thenReturn(overdueBooks);
        
        List<IBook> result = library.searchBook(searchOverdue);
        
        assertTrue(result.contains(book1), 
            "Book borrowed on January 30 should be overdue on March 1");
    }

    @Test
    @DisplayName("S9: Un abonné emprunte un livre - le stock est mis à jour")
    public void testS9_BorrowBookUpdatesStock() {
        doNothing().when(library).borrowBook(book1, johnny);
        
        library.borrowBook(book1, johnny);
        
        verify(library).borrowBook(book1, johnny);
    }

    @Test
    @DisplayName("S10: Un abonné retourne un livre dans les temps")
    public void testS10_ReturnBookOnTime() {
        doNothing().when(library).returnBook(book1, johnny);
        
        library.returnBook(book1, johnny);
        
        verify(library).returnBook(book1, johnny);
    }

    @Test
    @DisplayName("S11: Un abonné retourne un livre en retard - notification de retard")
    public void testS11_ReturnBookLate() {
        doAnswer(invocation -> {
            throw new IllegalStateException("Book returned late - notification sent");
        }).when(library).returnBook(book1, johnny);
        
        assertThrows(IllegalStateException.class,
            () -> library.returnBook(book1, johnny),
            "Late return should trigger notification");
    }

    @Test
    @DisplayName("S12a: Abonné premier sur la liste de réservation - emprunt réussi")
    public void testS12a_BorrowBookFirstInLine() {
        when(book1.getFirstInLine()).thenReturn(johnny);
        doNothing().when(library).borrowBook(book1, johnny);
        
        assertEquals(johnny, book1.getFirstInLine(), 
            "Subscriber should be first in line");
        
        library.borrowBook(book1, johnny);
        verify(library).borrowBook(book1, johnny);
    }

    @Test
    @DisplayName("S12b: Abonné pas premier sur la liste - emprunt refusé")
    public void testS12b_BorrowBookNotFirstInLine() {
        ISubscriber anotherSubscriber = mock(ISubscriber.class);
        when(anotherSubscriber.getId()).thenReturn(3);
        
        when(book1.getFirstInLine()).thenReturn(anotherSubscriber);
        
        doThrow(new IllegalStateException("You are not first in line for this book"))
            .when(library).borrowBook(book1, johnny);
        
        assertNotEquals(johnny, book1.getFirstInLine(), 
            "Subscriber should not be first in line");
        
        assertThrows(IllegalStateException.class,
            () -> library.borrowBook(book1, johnny),
            "Borrowing should fail if not first in line");
    }
}