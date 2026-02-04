package fr.imt;

import java.io.*;
import java.util.*;

public class Library implements ILibrary {

    private List<ISubscriber> subscribers;
    private List<IBooking> bookings;
    private List<IBook> catalogue;
    public static final String COMMA_DELIMITER = ",";


    public Library() {
        this.catalogue = new ArrayList<>(loadCatalogueFromCSV("./src/data/catalogue.csv"));
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
        if (subscriber != null && subscriber.getUsername().equals(username) && subscriber.getPassword().equals(password)) {
            return true;
        }
        throw new IllegalArgumentException("Invalid login");
    }

    @Override
    public boolean addSubscriber(ISubscriber subscriber) {
        this.subscribers.add(subscriber);
        return this.subscribers.contains(subscriber);
    }

    @Override
    public void returnBook(IBook book, ISubscriber subscriber) {

    }

    @Override
    public void borrowBook(IBook bookimport, ISubscriber subscriber) {

    }

    @Override
    public List<IBook> searchBooks(HashMap<String, String> mapSearch) {
        List<IBook> findedBooks = new ArrayList<>();
        for (IBook book : this.catalogue) {
            Map<String, String> mapBook = book.toHashMap();
            for (Map.Entry<String, String> entry : mapSearch.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (mapBook.containsKey(key) && mapBook.get(key).contains(value)) {
                    findedBooks.add(book);
                }
            }
        }
        return findedBooks;
    }


    @Override
    public void addBook(IBook book) {
        if (book instanceof Book) {
            this.catalogue.add(book);
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

    // Java
    @Override
    public List<Book> loadCatalogueFromCSV(String path) {
        List<Book> catalogue = new ArrayList<>();

        InputStream is = getClass().getResourceAsStream(path);
        try (BufferedReader br = is != null
                ? new BufferedReader(new InputStreamReader(is, java.nio.charset.StandardCharsets.UTF_8))
                : java.nio.file.Files.newBufferedReader(java.nio.file.Paths.get(path), java.nio.charset.StandardCharsets.UTF_8)) {

            String line;
            boolean first = true;
            while ((line = br.readLine()) != null) {
                if (first) { // skip header
                    first = false;
                    if (line.toLowerCase().contains("nom du livre") || line.toLowerCase().contains("num isbn")) {
                        continue;
                    }
                }
                String[] values = line.split(COMMA_DELIMITER);
                if (values.length < 4) continue;
                String title = values[0].replaceAll("^\"|\"$", "").trim();
                String isbn = values[1].replaceAll("^\"|\"$", "").trim();
                String stockStr = values[2].replaceAll("^\"|\"$", "").trim();
                String category = values[3].replaceAll("^\"|\"$", "").trim();

                int stock;
                try {
                    stock = Integer.parseInt(stockStr);
                } catch (NumberFormatException e) {
                    continue; // skip malformed line
                }
                catalogue.add(new Book(isbn, title, stock, category));
            }
        } catch (IOException e) {
            System.out.println("Error loading CSV: " + e);
            return List.of();
        }
        return catalogue;
    }

}
