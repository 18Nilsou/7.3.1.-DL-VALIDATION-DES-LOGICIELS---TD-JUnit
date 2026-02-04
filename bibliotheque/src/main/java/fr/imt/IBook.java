package fr.imt;

import java.util.HashMap;

public interface IBook {
    ISubscriber getFirstInLine();
    boolean addInLine(ISubscriber subscriber);
    String getCategory();
    String getTitle();

    HashMap<String, String> toHashMap();

}
