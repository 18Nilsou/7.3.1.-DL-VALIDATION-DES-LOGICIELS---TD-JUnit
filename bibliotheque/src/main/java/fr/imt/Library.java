package fr.imt;
import java.util.Date;

import java.util.HashMap;
import java.util.List;

public interface Library {
    boolean logIn(String username, String password);
    void getBooking(Book book, Subscriber subscriber, Date beginDate);
    void returnBook(Book book, Subscriber subscriber);
    void borrowBook(Book book, Subscriber subscriber);
    List<Book> searchBook(HashMap<String,String> mapSearch);
    boolean addSubscriber(Subscriber subscriber);
    void addBook(Book book);
}
