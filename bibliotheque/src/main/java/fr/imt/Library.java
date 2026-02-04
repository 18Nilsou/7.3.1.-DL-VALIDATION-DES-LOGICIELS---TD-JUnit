package fr.imt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Library implements ILibrary {

    private List<ISubscriber> subscribers;
    private List<Book> catalogue;
    public static final String COMMA_DELIMITER = ",";


    public Library(String pathCatalogue) {
        this.catalogue = loadCatalogueFromCSV(pathCatalogue);
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
    @Override
    public List<Book> loadCatalogueFromCSV(String path) {
        List<Book> catalogue = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                String title = values[0];
                int isbn = Integer.parseInt(values[1]);
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
