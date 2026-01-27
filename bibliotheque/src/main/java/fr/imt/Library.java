package fr.imt;

public interface Library {
    boolean logIn(int id, String username, String password);
    void getBooking(Book book, Subscriber subscriber, Date beginDate);
    void returnBook(Book book, Subscriber subscriber);
    void borrowBook(Book book, Subscriber subscriber);
    Book searchBook(String title);
}
