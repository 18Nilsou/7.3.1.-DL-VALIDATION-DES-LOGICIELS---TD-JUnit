package fr.imt;

import java.util.HashMap;

public class Book implements IBook {

    private String title;
    private String isbn;
    private int stock;
    private String category;


    public Book(String isbn, String title, int stock, String category) {
        this.category = category;
        this.isbn = isbn;
        this.title = title;
        this.stock = stock;
    }

    @Override
    public ISubscriber getFirstInLine() {
        return null;
    }

    @Override
    public boolean addInLine(ISubscriber subscriber) {
        return false;
    }

    @Override
    public String getType() {
        return "";
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
}
