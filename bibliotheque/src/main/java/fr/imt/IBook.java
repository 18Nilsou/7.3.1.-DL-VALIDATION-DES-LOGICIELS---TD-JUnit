package fr.imt;

import java.util.HashMap;

public interface IBook {
    ISubscriber getFirstInLine();
    boolean addInLine(ISubscriber subscriber);
    String getCategory();
    String getTitle();
    String getIsbn();
    int getStock();
    void decrementStock();
    void incrementStock();

    HashMap<String, String> toHashMap();

}
