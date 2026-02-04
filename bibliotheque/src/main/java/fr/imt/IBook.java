package fr.imt;

public interface IBook {
    ISubscriber getFirstInLine();
    boolean addInLine(ISubscriber subscriber);
    String getType();
}
