package fr.imt;
import java.util.Date;

import java.util.HashMap;
import java.util.List;

public interface ILibrary {
    boolean logIn(String username, String password);
    
    void returnBook(IBook book, ISubscriber subscriber);
    void borrowBook(IBook book, ISubscriber subscriber);
    List<IBook> searchBook(HashMap<String,String> mapSearch);
    boolean addSubscriber(ISubscriber subscriber);
    void addBook(IBook book);
    void getBooking(IBook book, ISubscriber subscriber);
    void getBookingByBook(IBook book);
    boolean addBooking(IBook book, ISubscriber subscriber, Date beginDate);
}
