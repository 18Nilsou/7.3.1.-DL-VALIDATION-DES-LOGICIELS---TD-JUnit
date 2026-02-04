package fr.imt;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Library implements ILibrary {
    @Override
    public boolean logIn(String username, String password) {
        return false;
    }

    @Override
    public void returnBook(IBook book, ISubscriber subscriber) {

    }

    @Override
    public void borrowBook(IBook book, ISubscriber subscriber) {

    }

    @Override
    public List<IBook> searchBook(HashMap<String, String> mapSearch) {
        return List.of();
    }

    @Override
    public boolean addSubscriber(ISubscriber subscriber) {
        return false;
    }

    @Override
    public void addBook(IBook book) {

    }

    @Override
    public void getBooking(IBook book, ISubscriber subscriber) {

    }

    @Override
    public void getBookingByBook(IBook book) {

    }

    @Override
    public boolean addBooking(IBook book, ISubscriber subscriber, Date beginDate) {
        return false;
    }
}
