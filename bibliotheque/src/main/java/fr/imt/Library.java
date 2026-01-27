package fr.imt;
import java.util.Date;
import java.util.List.Map;

public interface Library {
    boolean logIn(int id, String username, String password);
    void getBooking(Book book, Subscriber subscriber, Date beginDate);
    void returnBook(Book book, Subscriber subscriber);
    void borrowBook(Book book, Subscriber subscriber);
    Book searchBook(String title);
    boolean addSubscriber(Subscriber subscriber);
}
