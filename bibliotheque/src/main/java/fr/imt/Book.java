package fr.imt;

import java.util.HashMap;

public class Book implements IBook {

    private String title;
    private String isbn;
    private int stock;
    private String category;
    private List<ISubscriber> waitlist;

    public Book(String isbn, String title, int stock, String category) {
        this.category = category;
        this.isbn = isbn;
        this.title = title;
        this.stock = stock;
        this.waitlist = new List<>();
    }

    @Override
    public String getCategory() {
        return this.category;
    }
    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public HashMap<String, String> toHashMap() {
        HashMap<String, String> details = new HashMap<>();
        details.put("title", this.title);
        details.put("isbn", this.isbn);
        details.put("stock", Integer.toString(this.stock));
        details.put("category", this.category);
        return details;
    }

    //ajouter la fonction Equal :
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Book book = (Book) obj;
        return isbn.equals(book.isbn);
    }
    @Override
    public int getStock() {
        return stock;
    }
    @Override
    public void decrementStock() {
        this.stock--;
    }
    @Override
    public void incrementStock() {
        this.stock++;
    }

    @Override
    public ISubscriber getFirstInLine() {
        if (this.waitlist.isEmpty()) {
            return null;
        }
        ISubscriber firstInLine = this.waitlist.get(0);
        this.waitlist.remove(0);

        return firstInLine;
    }

    @Override
    public boolean addInLine(ISubscriber subscriber) {
        return this.waitlist.add(subscriber);
    }
}
