package fr.imt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Library implements ILibrary {

    
    private List<ISubscriber> subscribers;
    private List<IBooking> bookings;
    private List<IBook> catalogue;
    public static final String COMMA_DELIMITER = ",";


    public Library() {
        this.catalogue = new ArrayList<>(loadCatalogueFromCSV("../data/catalogue.csv"));
        this.subscribers = new java.util.ArrayList<>();
        this.bookings = new java.util.ArrayList<>();
    }

    public Library(String cataloguePath) {
        this.catalogue = new ArrayList<>(loadCatalogueFromCSV(cataloguePath));
        this.subscribers = new java.util.ArrayList<>();
        this.bookings = new java.util.ArrayList<>();
    }

    public List<IBook> getCatalogue() {
        return this.catalogue;
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
        if (book != null && book instanceof Book) {
            this.catalogue.add((Book) book);
        }
    }

    @Override
    public void getBooking(IBook book, ISubscriber subscriber) {

    }

    @Override
    public void getBookingByBook(IBook book) {

    }

    @Override
    public boolean addBooking(IBook book, ISubscriber subscriber, Date beginDate) {
        if (book == null || !catalogue.contains(book)) {
            throw new IllegalArgumentException("Book does not exist in catalog");
        }

        boolean alreadyBooked = bookings.stream()
            .anyMatch(booking -> {
                if (!booking.getBook().equals(book)) {
                    return false;
                }
                Calendar cal = Calendar.getInstance();
                cal.setTime(booking.getBeginDate());
                cal.add(Calendar.DAY_OF_MONTH, 30);
                Date endDate = cal.getTime();
                return endDate.compareTo(beginDate) >= 0;
            });

        if (alreadyBooked) {
            throw new IllegalArgumentException("Book is already booked for the given period");
        }

        bookings.add(new Booking(book, subscriber, beginDate));
        return true;
    }

    @Override
    public List<Book> loadCatalogueFromCSV(String path) {
        List<Book> catalogue = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                String title = values[0];
                String isbn = values[1];
                int stock = Integer.parseInt(values[2]);
                String category = values[3];

                catalogue.add(new Book(isbn, title, stock, category));
            }
        }
        catch (IOException e) {
            System.out.println("Error : " + e);
            return List.of();
        }
        return catalogue;
    }
}
