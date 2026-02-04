package fr.imt;

public class Book implements IBook {

    private String title;
    private String isbn;
    private int nbDisponible;
    private String genre;


    public Book(String isbn, String title, int nbDisponible, String genre) {
        this.genre = genre;
        this.isbn = isbn;
        this.title = title;
        this.nbDisponible = nbDisponible;
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
        return this.genre;
    }
    @Override
    public String getTitle() {
        return this.title;
    }
}
