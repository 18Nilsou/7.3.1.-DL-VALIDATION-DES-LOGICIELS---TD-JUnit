package fr.imt;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public interface ILibrary {
    boolean logIn(String username, String password);

    BookingState returnBook(IBook book, ISubscriber subscriber);
    void borrowBook(IBook book, ISubscriber subscriber);

    IBooking findBooking(IBook book, ISubscriber subscriber);

    List<IBooking> findBookingByBook(IBook book);

    List<IBook> searchBooks(HashMap<String,String> mapSearch);
    
    boolean addSubscriber(ISubscriber subscriber);
    void addBook(IBook book);
    boolean addBooking(IBook book, ISubscriber subscriber, Date beginDate);
    
    List<Book> loadCatalogueFromCSV(String path);
    List<IBook> getCatalogue();
    List<IBook> getLateBookings(ISubscriber subscriber);

    void addInQueue(IBook book, ISubscriber subscriber);
    boolean borrowBookForTheQueue(IBook book);
}
