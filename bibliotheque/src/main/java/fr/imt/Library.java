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

    public List<IBook> getLateBookings(ISubscriber subscriber) {
        List<IBook> lateBooks = new ArrayList<>();
        Date currentDate = new Date();
        for (IBooking booking : bookings) {
            if (!booking.getSubscriber().equals(subscriber)) {
                continue;
            }
            Calendar cal = Calendar.getInstance();
            cal.setTime(booking.getBeginDate());
            cal.add(Calendar.DAY_OF_MONTH, 30);
            Date dueDate = cal.getTime();
            if (currentDate.after(dueDate)) {
                booking.setStatus(BookingState.LATE);
                lateBooks.add(booking.getBook());
            }
        }
        return lateBooks;
    }

    @Override
    public BookingState returnBook(IBook book, ISubscriber subscriber) {
        IBooking booking = findBooking(book, subscriber);

        if (booking.getStatus() != BookingState.BORROW && booking.getStatus() != BookingState.LATE) {
            throw new IllegalArgumentException("Book is not in BORROW or LATE state");
        }

        if (getLateBookings(subscriber).contains(book)) {
            booking.setStatus(BookingState.RETURN_LATE);
        } else {
            booking.setStatus(BookingState.RETURN);
        }

        for (IBook b : catalogue) {
            if (b.equals(book)) {
                ((Book) b).incrementStock();
                break;
            }
        }

        return booking.getStatus();
    }

    @Override
    public void borrowBook(IBook book, ISubscriber subscriber) {
        IBooking booking = findBooking(book, subscriber);
        if (booking.getStatus() != BookingState.BOOKED) {
            throw new IllegalArgumentException("Book is not in BOOKED state");
        }
        if (booking.getBeginDate().after(new Date())) {
            throw new IllegalArgumentException("Booking start date is in the future");
        }

        for (IBook b : catalogue) {
            if (b.equals(book)) {
                if (((Book) b).getStock() <= 0) {
                    throw new IllegalArgumentException("No stock available for this book");
                }
                ((Book) b).decrementStock();
                break;
            }
        }

        booking.setStatus(BookingState.BORROW);
    }

    private IBooking findBooking(IBook book, ISubscriber subscriber) {
        return bookings.stream()
            .filter(b -> b.getBook().equals(book) && b.getSubscriber().equals(subscriber))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
    }

    @Override
    public List<IBook> searchBooks(HashMap<String,String> mapSearch) {
        List<IBook> results = new ArrayList<>(catalogue);

        if (mapSearch.containsKey("title")) {
            String title = mapSearch.get("title").toLowerCase();
            results.removeIf(book -> !((Book) book).getTitle().toLowerCase().contains(title));
        }
        if (mapSearch.containsKey("category")) {
            String category = mapSearch.get("category").toLowerCase();
            results.removeIf(book -> !book.getCategory().toLowerCase().equals(category));
        }

        return results;
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
