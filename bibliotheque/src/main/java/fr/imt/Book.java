package fr.imt;

public interface Book {
    Subscriber getFirstInLine();
    boolean addInLine(Subscriber subscriber);
}
