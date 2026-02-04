package fr.imt;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Library implements ILibrary {
    
    private List<ISubscriber> subscribers;

    public Library() {
        this.subscribers = new java.util.ArrayList<>();
    }
    
    @Override
    public boolean logIn(String username, String password) {
        ISubscriber subscriber = subscribers.stream()
                .filter(sub -> sub.getUsername().equals(username))
                .findFirst()
                .orElse(null);
        if (subscriber.getUsername().equals(username) && subscriber.getPassword().equals(password)) {
            return true;
        }
        throw new IllegalArgumentException("Invalid login");
    }

    @Override
    public boolean addSubscriber(ISubscriber subscriber) {
        if (this.subscribers.add(subscriber)) {
            return true;
        }
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
